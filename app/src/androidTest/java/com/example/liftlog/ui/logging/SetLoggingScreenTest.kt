package com.example.liftlog.ui.logging

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.data.local.entities.ExerciseEntity
import com.example.liftlog.data.local.entities.LogSetEntity
import com.example.liftlog.ui.RepositoryComposeTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SetLoggingScreenTest : RepositoryComposeTest() {
    @Before
    fun seedExercise() {
        runBlocking { exerciseDao.insertExercise(ExerciseEntity(id = 1, name = "Squat")) }
    }

    @Test
    fun previously_logged_sets_are_displayed() {
        runBlocking { logSetDao.insertSet(LogSetEntity(exerciseId = 1, weight = 225f, reps = 5)) }

        composeTestRule.setContent {
            MaterialTheme {
                SetLoggingScreen(
                    exerciseName = "Squat",
                    viewModel =
                        viewModel(
                            factory =
                                SetLoggingViewModel.provideFactory(
                                    repository,
                                    exerciseId = 1,
                                ),
                        ),
                    onBackClick = {},
                )
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("225.0 kg").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun logging_a_set_adds_it_to_the_history() {
        composeTestRule.setContent {
            MaterialTheme {
                SetLoggingScreen(
                    exerciseName = "Squat",
                    viewModel =
                        viewModel(
                            factory =
                                SetLoggingViewModel.provideFactory(
                                    repository,
                                    exerciseId = 1,
                                ),
                        ),
                    onBackClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Weight (kg)").performTextInput("135")
        composeTestRule.onNodeWithText("Reps").performTextInput("8")
        composeTestRule.onNodeWithText("Log Set").performClick()

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("135.0 kg").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun back_button_invokes_the_callback() {
        var backClicked = false

        composeTestRule.setContent {
            MaterialTheme {
                SetLoggingScreen(
                    exerciseName = "Squat",
                    viewModel =
                        viewModel(
                            factory =
                                SetLoggingViewModel.provideFactory(
                                    repository,
                                    exerciseId = 1,
                                ),
                        ),
                    onBackClick = { backClicked = true },
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

        assertTrue(backClicked)
    }
}
