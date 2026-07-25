package com.example.liftlog.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.data.local.AppDatabase
import com.example.liftlog.data.local.dao.ExerciseDao
import com.example.liftlog.data.local.dao.LogSetDao
import com.example.liftlog.data.local.entities.ExerciseEntity
import com.example.liftlog.data.local.entities.LogSetEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogSetDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var exerciseDao: ExerciseDao
    private lateinit var logSetDao: LogSetDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        exerciseDao = database.exerciseDao()
        logSetDao = database.logSetDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertSet_for_exercise_returns_correct_sets() = runBlocking {
        exerciseDao.insertExercise(ExerciseEntity(id = 10, name = "Squat"))

        logSetDao.insertSet(LogSetEntity(exerciseId = 10, weight = 225f, reps = 5))
        logSetDao.insertSet(LogSetEntity(exerciseId = 10, weight = 225f, reps = 5))

        val sets = logSetDao.getSetsForExercise(10).first()

        Assert.assertEquals(2, sets.size)
        Assert.assertEquals(225f, sets[0].weight)
    }
}

