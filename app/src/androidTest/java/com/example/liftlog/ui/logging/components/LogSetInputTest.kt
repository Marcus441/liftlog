package com.example.liftlog.ui.logging.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogSetInputTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displays_the_current_weight_and_reps() {
        composeTestRule.setContent {
            MaterialTheme {
                LogSetInput(
                    weightInput = "135",
                    onWeightChange = {},
                    repsInput = "8",
                    onRepsChange = {},
                    onButtonClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("135").assertExists()
        composeTestRule.onNodeWithText("8").assertExists()
    }

    @Test
    fun typing_a_weight_updates_the_field() {
        composeTestRule.setContent {
            var weight by remember { mutableStateOf("") }
            MaterialTheme {
                LogSetInput(
                    weightInput = weight,
                    onWeightChange = { weight = it },
                    repsInput = "",
                    onRepsChange = {},
                    onButtonClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Weight (kg)").performTextInput("135")

        composeTestRule.onNodeWithText("135").assertExists()
    }

    @Test
    fun typing_reps_updates_the_field() {
        composeTestRule.setContent {
            var reps by remember { mutableStateOf("") }
            MaterialTheme {
                LogSetInput(
                    weightInput = "",
                    onWeightChange = {},
                    repsInput = reps,
                    onRepsChange = { reps = it },
                    onButtonClick = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Reps").performTextInput("8")

        composeTestRule.onNodeWithText("8").assertExists()
    }

    @Test
    fun clicking_log_set_invokes_the_callback() {
        var clicked = false

        composeTestRule.setContent {
            MaterialTheme {
                LogSetInput(
                    weightInput = "135",
                    onWeightChange = {},
                    repsInput = "8",
                    onRepsChange = {},
                    onButtonClick = { clicked = true },
                )
            }
        }

        composeTestRule.onNodeWithText("Log Set").performClick()

        assertTrue(clicked)
    }
}
