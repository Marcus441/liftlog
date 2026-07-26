package com.example.liftlog.ui.exercises.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddExerciseInputTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displays_the_current_exercise_name() {
        composeTestRule.setContent {
            MaterialTheme {
                AddExerciseInput(
                    exerciseName = "Squat",
                    onExerciseNameChange = {},
                    onAddClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Squat").assertExists()
    }

    @Test
    fun typing_a_name_updates_the_field() {
        composeTestRule.setContent {
            var exerciseName by remember { mutableStateOf("") }
            MaterialTheme {
                AddExerciseInput(
                    exerciseName = exerciseName,
                    onExerciseNameChange = { exerciseName = it },
                    onAddClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Exercise Name").performTextInput("Deadlift")

        composeTestRule.onNodeWithText("Deadlift").assertExists()
    }

    @Test
    fun clicking_add_invokes_the_callback() {
        var addClicked = false

        composeTestRule.setContent {
            MaterialTheme {
                AddExerciseInput(
                    exerciseName = "Squat",
                    onExerciseNameChange = {},
                    onAddClick = { addClicked = true },
                )
            }
        }

        composeTestRule.onNodeWithText("Add").performClick()

        assertTrue(addClicked)
    }
}
