package com.example.liftlog.ui.navigation.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.ui.navigation.BottomNavScreen
import com.example.liftlog.ui.navigation.Route
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BottomNavigationBarTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displays_all_bottom_nav_items() {
        composeTestRule.setContent {
            MaterialTheme {
                BottomNavigationBar(currentRoute = Route.ExerciseList.path, onScreenSelected = {})
            }
        }

        composeTestRule.onNodeWithText("Exercises").assertExists()
        composeTestRule.onNodeWithText("History").assertExists()
        composeTestRule.onNodeWithText("Workouts").assertExists()
    }

    @Test
    fun current_route_item_is_selected() {
        composeTestRule.setContent {
            MaterialTheme {
                BottomNavigationBar(currentRoute = Route.WorkoutHistory.path, onScreenSelected = {})
            }
        }

        composeTestRule.onNodeWithText("History").assertIsSelected()
        composeTestRule.onNodeWithText("Exercises").assertIsNotSelected()
        composeTestRule.onNodeWithText("Workouts").assertIsNotSelected()
    }

    @Test
    fun clicking_a_tab_invokes_the_callback_with_that_screen() {
        var selectedScreen: BottomNavScreen? = null

        composeTestRule.setContent {
            MaterialTheme {
                BottomNavigationBar(
                    currentRoute = Route.ExerciseList.path,
                    onScreenSelected = { selectedScreen = it },
                )
            }
        }

        composeTestRule.onNodeWithText("Workouts").performClick()

        assertEquals(Route.Workouts, selectedScreen?.route)
    }
}
