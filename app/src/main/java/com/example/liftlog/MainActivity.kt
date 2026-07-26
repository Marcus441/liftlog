package com.example.liftlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.liftlog.ui.exercises.ExerciseListScreen
import com.example.liftlog.ui.exercises.ExerciseViewModel
import com.example.liftlog.ui.history.WorkoutHistoryScreen
import com.example.liftlog.ui.logging.SetLoggingScreen
import com.example.liftlog.ui.logging.SetLoggingViewModel
import com.example.liftlog.ui.navigation.Screen
import com.example.liftlog.ui.navigation.bottomNavScreens
import com.example.liftlog.ui.navigation.components.BottomNavigationBar
import com.example.liftlog.ui.workouts.WorkoutsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as LiftLogApplication
        val repository = app.repository

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    if (bottomNavScreens.any { it.route == currentRoute }) {
                        BottomNavigationBar(
                            currentRoute = currentRoute,
                            onScreenSelected = { screen ->
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                },
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.ExerciseCatalog.route,
                    modifier = Modifier.padding(innerPadding),
                ) {
                    composable(Screen.ExerciseCatalog.route) {
                        val viewModel: ExerciseViewModel =
                            viewModel(
                                factory = ExerciseViewModel.provideFactory(repository),
                            )
                        ExerciseListScreen(
                            viewModel = viewModel,
                            onExerciseClick = { id, name ->
                                navController.navigate("set_logging/$id/$name")
                            },
                        )
                    }

                    composable(
                        route = "set_logging/{exerciseId}/{exerciseName}",
                        arguments =
                            listOf(
                                navArgument("exerciseId") { type = NavType.IntType },
                                navArgument("exerciseName") { type = NavType.StringType },
                            ),
                    ) { backStackEntry ->
                        val exerciseId =
                            backStackEntry.arguments?.getInt("exerciseId") ?: return@composable
                        val exerciseName = backStackEntry.arguments?.getString("exerciseName") ?: ""

                        val viewModel: SetLoggingViewModel =
                            viewModel(
                                factory = SetLoggingViewModel.provideFactory(repository, exerciseId),
                            )

                        SetLoggingScreen(
                            exerciseName = exerciseName,
                            viewModel = viewModel,
                            onBackClick = { navController.popBackStack() },
                        )
                    }

                    composable(Screen.WorkoutHistory.route) {
                        WorkoutHistoryScreen()
                    }

                    composable(Screen.Workouts.route) {
                        WorkoutsScreen()
                    }
                }
            }
        }
    }
}
