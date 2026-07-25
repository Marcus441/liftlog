package com.example.liftlog.repository

import androidx.room.withTransaction
import com.example.liftlog.data.local.AppDatabase
import com.example.liftlog.data.local.dao.ExerciseDao
import com.example.liftlog.data.local.dao.LogSetDao
import com.example.liftlog.data.local.entities.ExerciseEntity
import com.example.liftlog.data.local.entities.LogSetEntity
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(
    private val database: AppDatabase,
    private val exerciseDao: ExerciseDao,
    private val logSetDao: LogSetDao
) {
    val allExercises: Flow<List<ExerciseEntity>> = exerciseDao.getAllExercises()

    suspend fun addExercises(exerciseNames: List<String>) {
        val exercises = exerciseNames.map { name -> ExerciseEntity(name = name) }
        exerciseDao.insertExercises(exercises)
    }

    suspend fun addExercise(name: String) {
        exerciseDao.insertExercise(ExerciseEntity(name = name))
    }

    fun getSetsForExercise(exerciseId: Int): Flow<List<LogSetEntity>> {
        return logSetDao.getSetsForExercise(exerciseId)
    }

    suspend fun logSet(exerciseId: Int, weight: Float, reps: Int) {
        logSetDao.insertSet(LogSetEntity(exerciseId = exerciseId, weight = weight, reps = reps))
    }

    suspend fun deleteExercise(exercise: ExerciseEntity) {
        database.withTransaction {
            exerciseDao.deleteExercise(exercise)
            logSetDao.deleteSetsForExercise(exercise.id)
        }
    }
}
