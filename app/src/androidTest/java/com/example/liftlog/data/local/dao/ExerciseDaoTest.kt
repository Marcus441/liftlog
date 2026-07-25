package com.example.liftlog.data.local.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.data.local.AppDatabaseTest
import com.example.liftlog.data.local.entities.ExerciseEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExerciseDaoTest : AppDatabaseTest() {

    @Test
    fun inserting_multiple_exercises_returns_them_all() = runBlocking {
        val defaultExercises =
            listOf(
                ExerciseEntity(id = 1, name = "Bench Press"),
                ExerciseEntity(id = 2, name = "Barbell Squat"),
                ExerciseEntity(id = 3, name = "Deadlift")
            )
        exerciseDao.insertExercises(defaultExercises)

        val allExercises = exerciseDao.getAllExercises().first()

        assertEquals(3, allExercises.size)
        assertEquals("Barbell Squat", allExercises[0].name)
        assertEquals("Bench Press", allExercises[1].name)
        assertEquals("Deadlift", allExercises[2].name)
    }

    @Test
    fun inserting_a_single_exercise_returns_it() = runBlocking {
        exerciseDao.insertExercise(ExerciseEntity(name = "Bench Press"))

        val allExercises = exerciseDao.getAllExercises().first()

        assertEquals(1, allExercises.size)
        assertEquals("Bench Press", allExercises[0].name)
    }
}
