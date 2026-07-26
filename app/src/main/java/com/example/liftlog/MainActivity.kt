package com.example.liftlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.liftlog.ui.navigation.LiftLogNavHost
import com.example.liftlog.ui.navigation.components.BottomNavigationBar
import com.example.liftlog.ui.navigation.isBottomNavRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as LiftLogApplication
        val repository = app.repository

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    if (isBottomNavRoute(currentRoute)) {
                        BottomNavigationBar(
                            currentRoute = currentRoute,
                            onScreenSelected = { screen ->
                                navController.navigate(screen.route.path) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                },
            ) { innerPadding ->
                LiftLogNavHost(
                    repository = repository,
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }
    }
}
