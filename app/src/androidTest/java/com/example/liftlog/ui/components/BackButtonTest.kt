package com.example.liftlog.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BackButtonTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun clicking_invokes_the_callback() {
        var clickCount = 0

        composeTestRule.setContent {
            MaterialTheme {
                BackButton(onBackClick = { clickCount++ })
            }
        }

        composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

        assertEquals(1, clickCount)
    }

    @Test
    fun rapid_clicks_only_invoke_the_callback_once() {
        var clickCount = 0

        composeTestRule.setContent {
            MaterialTheme {
                BackButton(onBackClick = { clickCount++ })
            }
        }

        val backButton = composeTestRule.onNodeWithContentDescription("Navigate Back")
        backButton.performClick()
        backButton.performClick()
        backButton.performClick()

        assertEquals(1, clickCount)
    }
}
