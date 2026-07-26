package com.example.liftlog.ui.navigation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BottomNavScreenTest {
    @Test
    fun bottom_nav_screens_are_bottom_nav_routes() {
        assertTrue(isBottomNavRoute(Route.ExerciseList.path))
        assertTrue(isBottomNavRoute(Route.WorkoutHistory.path))
        assertTrue(isBottomNavRoute(Route.Workouts.path))
    }

    @Test
    fun detail_routes_are_not_bottom_nav_routes() {
        assertFalse(isBottomNavRoute(Route.SetLogging.path))
    }

    @Test
    fun null_route_is_not_a_bottom_nav_route() {
        assertFalse(isBottomNavRoute(null))
    }
}
