package com.example.liftlog.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.liftlog.data.local.AppDatabaseTest
import com.example.liftlog.repository.ExerciseRepository
import org.junit.Before
import org.junit.Rule

abstract class RepositoryComposeTest : AppDatabaseTest() {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    protected lateinit var repository: ExerciseRepository

    @Before
    fun setUpRepository() {
        repository = ExerciseRepository(database, exerciseDao, logSetDao)
    }
}
