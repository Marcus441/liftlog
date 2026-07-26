package com.example.liftlog.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.liftlog.data.local.entities.LogSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogSetDao {
    @Query("SELECT * FROM log_sets WHERE exerciseId = :exerciseId ORDER BY timestamp DESC")
    fun getSetsForExercise(exerciseId: Int): Flow<List<LogSetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: LogSetEntity)

    @Query("DELETE FROM log_sets WHERE exerciseId = :exerciseId")
    suspend fun deleteSetsForExercise(exerciseId: Int): Int
}
