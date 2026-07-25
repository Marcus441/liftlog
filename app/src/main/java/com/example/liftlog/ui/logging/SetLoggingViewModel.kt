package com.example.liftlog.ui.logging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.liftlog.data.local.entities.LogSetEntity
import com.example.liftlog.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SetLoggingViewModel(
    private val repository: ExerciseRepository,
    private val exerciseId: Int
) : ViewModel() {

    private val _selectedExerciseId = MutableStateFlow<Int?>(null)

    val loggedSets: StateFlow<List<LogSetEntity>> = repository.getSetsForExercise(exerciseId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun logSet(weightString: String, repsString: String) {
        val weight = weightString.trim().replace(',', '.').toFloatOrNull()
        val reps = repsString.trim().toIntOrNull()

        if (weight == null || reps == null) {
            android.util.Log.e("LiftLogDebug", "Validation failed: weight=$weight, reps=$reps")
            return
        }

        viewModelScope.launch {
            try {
                android.util.Log.d("LiftLogDebug", "Attempting DB insert for exerciseId=$exerciseId...")
                repository.logSet(
                    exerciseId = exerciseId,
                    weight = weight,
                    reps = reps
                )
                android.util.Log.d("LiftLogDebug", "Insert call completed successfully!")
            } catch (e: Exception) {
                android.util.Log.e("LiftLogDebug", "DB Write Failed!", e)
            }
        }
    }
    companion object {
        fun provideFactory(
            repository: ExerciseRepository,
            exerciseId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SetLoggingViewModel(repository, exerciseId) as T
            }
        }
    }
}
