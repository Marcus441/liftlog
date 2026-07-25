package com.example.liftlog.data.local.dao

import com.example.liftlog.data.local.entities.ExerciseEntity
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao{
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAllExercises(): Flow<List<ExerciseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercise: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)
}
