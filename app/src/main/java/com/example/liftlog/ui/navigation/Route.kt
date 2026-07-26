package com.example.liftlog.ui.navigation

sealed interface Route {
    val path: String

    data object ExerciseList : Route {
        override val path = "exercise_list"
    }

    data object WorkoutHistory : Route {
        override val path = "history"
    }

    data object Workouts : Route {
        override val path = "workouts"
    }

    data object SetLogging : Route {
        override val path = "set_logging/{exerciseId}/{exerciseName}"

        const val ARG_EXERCISE_ID = "exerciseId"
        const val ARG_EXERCISE_NAME = "exerciseName"

        fun build(
            exerciseId: Int,
            exerciseName: String,
        ) = "set_logging/$exerciseId/$exerciseName"
    }
}
