package com.example.liftlog.data.local.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.liftlog.data.local.AppDatabaseTest
import com.example.liftlog.data.local.entities.ExerciseEntity
import com.example.liftlog.data.local.entities.LogSetEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogSetDaoTest : AppDatabaseTest() {
    @Test
    fun inserting_sets_for_an_exercise_returns_them() =
        runBlocking {
            exerciseDao.insertExercise(ExerciseEntity(id = 10, name = "Squat"))

            logSetDao.insertSet(LogSetEntity(exerciseId = 10, weight = 225f, reps = 5))
            logSetDao.insertSet(LogSetEntity(exerciseId = 10, weight = 225f, reps = 5))

            val sets = logSetDao.getSetsForExercise(10).first()

            assertEquals(2, sets.size)
            assertEquals(225f, sets[0].weight)
        }
}
