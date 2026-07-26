package com.example.liftlog.ui.exercises

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.liftlog.ui.exercises.components.AddExerciseInput
import com.example.liftlog.ui.exercises.components.ExerciseItem

@Composable
fun ExerciseListScreen(
    viewModel: ExerciseViewModel,
    onExerciseClick: (exerciseId: Int, name: String) -> Unit,
) {
    val exerciseList by viewModel.exercises.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Text(
            text = "Exercise Catalog",
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddExerciseInput(
            exerciseName = inputText,
            onExerciseNameChange = { inputText = it },
            onAddClick = {
                viewModel.addExercise(name = inputText)
                inputText = ""
            },
        )

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = exerciseList,
                key = { exercise -> exercise.id },
            ) { exercise ->
                ExerciseItem(
                    exercise = exercise,
                    onDelete = { viewModel.deleteExercise(exercise) },
                    onClick = { onExerciseClick(exercise.id, exercise.name) },
                )
            }
        }
    }
}
