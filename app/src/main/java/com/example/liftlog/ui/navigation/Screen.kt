package com.example.liftlog.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    data object ExerciseCatalog : Screen(
        route = "exercise_list",
        label = "Exercises",
        icon = Icons.AutoMirrored.Filled.List,
    )

    data object WorkoutHistory : Screen(route = "history", label = "History", icon = Icons.Default.DateRange)

    data object Workouts : Screen(route = "workouts", label = "Workouts", icon = Icons.Default.PlayArrow)
}

val bottomNavScreens = listOf(Screen.ExerciseCatalog, Screen.WorkoutHistory, Screen.Workouts)
