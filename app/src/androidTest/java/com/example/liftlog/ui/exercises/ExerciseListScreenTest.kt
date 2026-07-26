package com.example.liftlog.ui.exercises

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.data.local.entities.ExerciseEntity
import com.example.liftlog.data.local.entities.LogSetEntity
import com.example.liftlog.ui.RepositoryComposeTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseListScreenTest : RepositoryComposeTest() {
    @Test
    fun existing_exercises_are_displayed() {
        runBlocking { exerciseDao.insertExercise(ExerciseEntity(name = "Bench Press")) }

        composeTestRule.setContent {
            MaterialTheme {
                ExerciseListScreen(
                    viewModel = viewModel(factory = ExerciseViewModel.provideFactory(repository)),
                    onExerciseClick = { _, _ -> },
                )
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Bench Press").fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun adding_an_exercise_shows_it_in_the_list() {
        composeTestRule.setContent {
            MaterialTheme {
                ExerciseListScreen(
                    viewModel = viewModel(factory = ExerciseViewModel.provideFactory(repository)),
                    onExerciseClick = { _, _ -> },
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
    fun clicking_an_exercise_invokes_the_callback_with_its_id_and_name() {
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
                    },
                )
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Squat").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Squat").performClick()

        assertEquals(5, clickedId)
        assertEquals("Squat", clickedName)
    }

    @Test
    fun swiping_an_exercise_deletes_it_and_its_logged_sets() {
        val exerciseId = 42
        val exerciseName = "Overhead Press"

        runBlocking {
            exerciseDao.insertExercise(ExerciseEntity(id = exerciseId, name = exerciseName))
            logSetDao.insertSet(
                LogSetEntity(id = 1, exerciseId = exerciseId, weight = 135f, reps = 5),
            )
        }

        composeTestRule.setContent {
            MaterialTheme {
                ExerciseListScreen(
                    viewModel = viewModel(factory = ExerciseViewModel.provideFactory(repository)),
                    onExerciseClick = { _, _ -> },
                )
            }
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText(exerciseName).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText(exerciseName).performTouchInput {
            swipeLeft()
        }

        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText(exerciseName).fetchSemanticsNodes().isEmpty()
        }

        runBlocking {
            val allExercises = exerciseDao.getAllExercises().first()
            val remainingSets = logSetDao.getSetsForExercise(exerciseId).first()

            assertTrue(
                "Log sets should be empty after parent exercise deletion",
                remainingSets.isEmpty(),
            )
            assertTrue(
                "Exercise should no longer exist in database",
                allExercises.none { it.id == exerciseId },
            )
        }
    }
}
