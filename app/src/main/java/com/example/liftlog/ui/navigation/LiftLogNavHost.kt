package com.example.liftlog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.liftlog.repository.ExerciseRepository
import com.example.liftlog.ui.exercises.ExerciseListScreen
import com.example.liftlog.ui.exercises.ExerciseViewModel
import com.example.liftlog.ui.history.WorkoutHistoryScreen
import com.example.liftlog.ui.logging.SetLoggingScreen
import com.example.liftlog.ui.logging.SetLoggingViewModel
import com.example.liftlog.ui.workouts.WorkoutsScreen

@Composable
fun LiftLogNavHost(
    repository: ExerciseRepository,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Route.ExerciseList.path,
        modifier = modifier,
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
