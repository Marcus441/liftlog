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
class ExerciseDaoTest {
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
    fun insertExercises_and_read_them_back() = runBlocking {
        val defaultExercises: List<ExerciseEntity> = listOf(
            ExerciseEntity(id = 1, name = "Bench Press"),
            ExerciseEntity(id = 2, name = "Barbell Squat"),
            ExerciseEntity(id = 3, name = "Deadlift")
        )
        exerciseDao.insertExercises(defaultExercises)
        val allExercises = exerciseDao.getAllExercises().first()

        Assert.assertEquals(3, allExercises.size)
        Assert.assertEquals("Deadlift", allExercises[2].name)
        Assert.assertEquals("Bench Press", allExercises[1].name)
        Assert.assertEquals("Barbell Squat", allExercises[0].name)

    }
    @Test
    fun insertExercise_and_read_it_back() = runBlocking {

        exerciseDao.insertExercise(ExerciseEntity(name = "Bench Press"))
        val allExercises = exerciseDao.getAllExercises().first()

        Assert.assertEquals(1, allExercises.size)
        Assert.assertEquals("Bench Press", allExercises[0].name)
    }

}
