package com.example.liftlog.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.liftlog.data.local.dao.ExerciseDao
import com.example.liftlog.data.local.dao.LogSetDao
import com.example.liftlog.data.local.entities.ExerciseEntity
import com.example.liftlog.data.local.entities.LogSetEntity


@Database(
    entities = [ExerciseEntity::class, LogSetEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun logSetDao(): LogSetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gym_tracker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
