package com.example.liftlog.ui.exercises

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.data.local.AppDatabase
import com.example.liftlog.data.local.dao.ExerciseDao
import com.example.liftlog.data.local.entities.ExerciseEntity
import com.example.liftlog.repository.ExerciseRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var database: AppDatabase
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var repository: ExerciseRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        exerciseDao = database.exerciseDao()
        repository = ExerciseRepository(exerciseDao, database.logSetDao())
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun existingExercises_areDisplayed() {
        runBlocking { exerciseDao.insertExercise(ExerciseEntity(name = "Bench Press")) }

        composeTestRule.setContent {
            MaterialTheme {
                ExerciseListScreen(
                    viewModel = viewModel(factory = ExerciseViewModel.provideFactory(repository)),
                    onExerciseClick = { _, _ -> }
                )
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Bench Press").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun addingExercise_showsItInList() {
        composeTestRule.setContent {
            MaterialTheme {
                ExerciseListScreen(
                    viewModel = viewModel(factory = ExerciseViewModel.provideFactory(repository)),
                    onExerciseClick = { _, _ -> }
                )
            }
        }

        composeTestRule.onNodeWithText("Exercise Name").performTextInput("Deadlift")
        composeTestRule.onNodeWithText("Add").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Deadlift").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun clickingExercise_invokesCallbackWithIdAndName() {
        runBlocking { exerciseDao.insertExercise(ExerciseEntity(id = 5, name = "Squat")) }

        var clickedId: Int? = null
        var clickedName: String? = null

        composeTestRule.setContent {
            MaterialTheme {
                ExerciseListScreen(
                    viewModel = viewModel(factory = ExerciseViewModel.provideFactory(repository)),
                    onExerciseClick = { id, name ->
                        clickedId = id
                        clickedName = name
                    }
                )
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Squat").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Squat").performClick()

        Assert.assertEquals(5, clickedId)
        Assert.assertEquals("Squat", clickedName)
    }
}
