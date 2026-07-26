package com.example.liftlog.ui.exercises.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.liftlog.data.local.entities.ExerciseEntity
import com.example.liftlog.ui.components.SwipeToDeleteContainer

@Composable
fun ExerciseItem(
    exercise: ExerciseEntity,
    onDelete: () -> Unit,
    onClick: () -> Unit,
) {
    SwipeToDeleteContainer(onDelete = onDelete) {
        Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(exercise.name)
            }
        }
    }
}
