package com.example.liftlog.ui.logging.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.data.local.entities.LogSetEntity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoggedSetItemTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displays_the_weight_and_reps() {
        composeTestRule.setContent {
            MaterialTheme {
                LoggedSetItem(set = LogSetEntity(exerciseId = 1, weight = 225f, reps = 5))
            }
        }

        composeTestRule.onNodeWithText("225.0 kg").assertExists()
        composeTestRule.onNodeWithText("5 reps").assertExists()
    }
}
