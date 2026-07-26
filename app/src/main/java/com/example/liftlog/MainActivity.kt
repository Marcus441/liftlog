package com.example.liftlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.liftlog.ui.exercises.ExerciseListScreen
import com.example.liftlog.ui.exercises.ExerciseViewModel
import com.example.liftlog.ui.logging.SetLoggingScreen
import com.example.liftlog.ui.logging.SetLoggingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as LiftLogApplication
        val repository = app.repository

        setContent {
            val navController = rememberNavController()

            Box(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
                NavHost(navController = navController, startDestination = "exercise_list") {
                    composable("exercise_list") {
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
                }
            }
        }
    }
}
