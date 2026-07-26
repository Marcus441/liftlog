package com.example.liftlog.ui.navigation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.liftlog.ui.navigation.Screen
import com.example.liftlog.ui.navigation.bottomNavScreens

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onScreenSelected: (Screen) -> Unit,
) {
    NavigationBar {
        bottomNavScreens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { onScreenSelected(screen) },
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
            )
        }
    }
}
