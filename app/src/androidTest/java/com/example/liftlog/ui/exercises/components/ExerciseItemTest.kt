package com.example.liftlog.ui.exercises.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.data.local.entities.ExerciseEntity
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseItemTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displays_the_exercise_name() {
        composeTestRule.setContent {
            MaterialTheme {
                ExerciseItem(
                    exercise = ExerciseEntity(id = 1, name = "Bench Press"),
                    onDelete = {},
                    onClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Bench Press").assertExists()
    }

    @Test
    fun clicking_invokes_the_click_callback() {
        var clicked = false

        composeTestRule.setContent {
            MaterialTheme {
                ExerciseItem(
                    exercise = ExerciseEntity(id = 1, name = "Bench Press"),
                    onDelete = {},
                    onClick = { clicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Bench Press").performClick()

        assertTrue(clicked)
    }

    @Test
    fun swiping_invokes_the_delete_callback() {
        var deleted = false

        composeTestRule.setContent {
            MaterialTheme {
                ExerciseItem(
                    exercise = ExerciseEntity(id = 1, name = "Bench Press"),
                    onDelete = { deleted = true },
                    onClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Bench Press").performTouchInput { swipeLeft() }

        composeTestRule.waitUntil(timeoutMillis = 5_000) { deleted }
    }
}
