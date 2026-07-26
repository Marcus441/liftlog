package com.example.liftlog.ui.navigation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ScreenTest {
    @Test
    fun bottom_nav_screens_are_bottom_nav_routes() {
        assertTrue(isBottomNavRoute(Screen.ExerciseCatalog.route))
        assertTrue(isBottomNavRoute(Screen.WorkoutHistory.route))
        assertTrue(isBottomNavRoute(Screen.Workouts.route))
    }

    @Test
    fun detail_routes_are_not_bottom_nav_routes() {
        assertFalse(isBottomNavRoute("set_logging/1/Squat"))
    }

    @Test
    fun null_route_is_not_a_bottom_nav_route() {
        assertFalse(isBottomNavRoute(null))
    }
}
