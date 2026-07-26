package com.example.liftlog.ui.logging.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LogSetInput(
    weightInput: String,
    onWeightChange: (String) -> Unit,
    repsInput: String,
    onRepsChange: (String) -> Unit,
    onButtonClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = weightInput,
            onValueChange = onWeightChange,
            label = { Text("Weight (kg)") },
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedTextField(
            value = repsInput,
            onValueChange = onRepsChange,
            label = { Text("Reps") },
            modifier = Modifier.weight(1f),
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Button(
        onClick = onButtonClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text("Log Set")
    }
}
