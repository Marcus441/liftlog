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
import com.example.liftlog.ui.navigation.Route
import com.example.liftlog.ui.navigation.components.BottomNavigationBar
import com.example.liftlog.ui.navigation.isBottomNavRoute
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
                    if (isBottomNavRoute(currentRoute)) {
                        BottomNavigationBar(
                            currentRoute = currentRoute,
                            onScreenSelected = { screen ->
                                navController.navigate(screen.route.path) {
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
                    startDestination = Route.ExerciseList.path,
                    modifier = Modifier.padding(innerPadding),
                ) {
                    composable(Route.ExerciseList.path) {
                        val viewModel: ExerciseViewModel =
                            viewModel(
                                factory = ExerciseViewModel.provideFactory(repository),
                            )
                        ExerciseListScreen(
                            viewModel = viewModel,
                            onExerciseClick = { id, name ->
                                navController.navigate(Route.SetLogging.build(id, name))
                            },
                        )
                    }

                    composable(
                        route = Route.SetLogging.path,
                        arguments =
                            listOf(
                                navArgument(Route.SetLogging.ARG_EXERCISE_ID) { type = NavType.IntType },
                                navArgument(Route.SetLogging.ARG_EXERCISE_NAME) { type = NavType.StringType },
                            ),
                    ) { backStackEntry ->
                        val exerciseId =
                            backStackEntry.arguments?.getInt(Route.SetLogging.ARG_EXERCISE_ID)
                                ?: return@composable
                        val exerciseName =
                            backStackEntry.arguments?.getString(Route.SetLogging.ARG_EXERCISE_NAME) ?: ""

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

                    composable(Route.WorkoutHistory.path) {
                        WorkoutHistoryScreen()
                    }

                    composable(Route.Workouts.path) {
                        WorkoutsScreen()
                    }
                }
            }
        }
    }
}
