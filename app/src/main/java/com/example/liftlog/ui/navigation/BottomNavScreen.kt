package com.example.liftlog.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavScreen(
    val route: Route,
    val label: String,
    val icon: ImageVector,
)

val bottomNavScreens =
    listOf(
        BottomNavScreen(Route.ExerciseList, "Exercises", Icons.AutoMirrored.Filled.List),
        BottomNavScreen(Route.WorkoutHistory, "History", Icons.Default.DateRange),
        BottomNavScreen(Route.Workouts, "Workouts", Icons.Default.PlayArrow),
    )

fun isBottomNavRoute(route: String?): Boolean = bottomNavScreens.any { it.route.path == route }
