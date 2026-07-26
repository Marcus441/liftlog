package com.example.liftlog.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SwipeToDeleteContainerTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displays_the_wrapped_content() {
        composeTestRule.setContent {
            MaterialTheme {
                SwipeToDeleteContainer(onDelete = {}) {
                    Text("Item Content")
                }
            }
        }

        composeTestRule.onNodeWithText("Item Content").assertExists()
    }

    @Test
    fun swiping_left_invokes_the_delete_callback() {
        var deleted = false

        composeTestRule.setContent {
            MaterialTheme {
                SwipeToDeleteContainer(onDelete = { deleted = true }) {
                    Text("Item Content")
                }
            }
        }

        composeTestRule.onNodeWithText("Item Content").performTouchInput { swipeLeft() }

        composeTestRule.waitUntil(timeoutMillis = 5_000) { deleted }
    }

    @Test
    fun swiping_right_does_not_invoke_the_delete_callback() {
        var deleted = false

        composeTestRule.setContent {
            MaterialTheme {
                SwipeToDeleteContainer(onDelete = { deleted = true }) {
                    Text("Item Content")
                }
            }
        }

        composeTestRule.onNodeWithText("Item Content").performTouchInput { swipeRight() }
        composeTestRule.waitForIdle()

        assertFalse(deleted)
    }
}
