package com.example.liftlog.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.liftlog.data.local.dao.ExerciseDao
import com.example.liftlog.data.local.dao.LogSetDao
import org.junit.After
import org.junit.Before

abstract class AppDatabaseTest {
    protected lateinit var database: AppDatabase
    protected lateinit var exerciseDao: ExerciseDao
    protected lateinit var logSetDao: LogSetDao

    @Before
    fun setUpDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room
                .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        exerciseDao = database.exerciseDao()
        logSetDao = database.logSetDao()
    }

    @After
    fun tearDownDatabase() {
        database.close()
    }
}
