package com.example.liftlog.ui.logging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import com.example.liftlog.ui.components.BackButton
import com.example.liftlog.ui.logging.components.LogSetInput

@Composable
fun SetLoggingScreen(
    exerciseName: String,
    viewModel: SetLoggingViewModel,
    onBackClick: () -> Unit
) {
    val loggedSets by viewModel.loggedSets.collectAsState()
    var weightInput by remember { mutableStateOf("") }
    var repsInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BackButton(onBackClick)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = exerciseName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LogSetInput(
            weightInput = weightInput,
            onWeightChange = { weightInput = it },
            repsInput = repsInput,
            onRepsChange = { repsInput = it })

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.logSet(weightInput, repsInput)
                weightInput = ""
                repsInput = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Set")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Logged History",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(loggedSets) { set ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${set.weight} kg")
                        Text("${set.reps} reps")
                    }
                }
            }
        }
    }
}
