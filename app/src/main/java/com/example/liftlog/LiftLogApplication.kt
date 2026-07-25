package com.example.liftlog

import android.app.Application
import com.example.liftlog.data.local.AppDatabase
import com.example.liftlog.repository.ExerciseRepository

class LiftLogApplication : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ExerciseRepository(database.exerciseDao(), database.logSetDao()) }
}
