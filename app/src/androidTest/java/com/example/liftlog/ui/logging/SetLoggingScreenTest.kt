package com.example.liftlog.ui.logging

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.data.local.AppDatabase
import com.example.liftlog.data.local.dao.ExerciseDao
import com.example.liftlog.data.local.dao.LogSetDao
import com.example.liftlog.data.local.entities.ExerciseEntity
import com.example.liftlog.data.local.entities.LogSetEntity
import com.example.liftlog.repository.ExerciseRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SetLoggingScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var database: AppDatabase
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var logSetDao: LogSetDao
    private lateinit var repository: ExerciseRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        exerciseDao = database.exerciseDao()
        logSetDao = database.logSetDao()
        repository = ExerciseRepository(exerciseDao, logSetDao)

        runBlocking { exerciseDao.insertExercise(ExerciseEntity(id = 1, name = "Squat")) }
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun previouslyLoggedSets_areDisplayed() {
        runBlocking { logSetDao.insertSet(LogSetEntity(exerciseId = 1, weight = 225f, reps = 5)) }

        composeTestRule.setContent {
            MaterialTheme {
                SetLoggingScreen(
                    exerciseName = "Squat",
                    viewModel = viewModel(factory = SetLoggingViewModel.provideFactory(repository, exerciseId = 1)),
                    onBackClick = {}
                )
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("225.0 lbs").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun loggingSet_addsItToHistory() {
        composeTestRule.setContent {
            MaterialTheme {
                SetLoggingScreen(
                    exerciseName = "Squat",
                    viewModel = viewModel(factory = SetLoggingViewModel.provideFactory(repository, exerciseId = 1)),
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Weight (lbs)").performTextInput("135")
        composeTestRule.onNodeWithText("Reps").performTextInput("8")
        composeTestRule.onNodeWithText("Log Set").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("135.0 lbs").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun backButton_invokesCallback() {
        var backClicked = false

        composeTestRule.setContent {
            MaterialTheme {
                SetLoggingScreen(
                    exerciseName = "Squat",
                    viewModel = viewModel(factory = SetLoggingViewModel.provideFactory(repository, exerciseId = 1)),
                    onBackClick = { backClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

        Assert.assertTrue(backClicked)
    }
}
