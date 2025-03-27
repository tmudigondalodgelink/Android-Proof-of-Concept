package com.example.presentationmodule

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navController: NavController,
    navigatorView: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavSetup(
                navigateTo = { route -> navController.navigate(route) {
                    launchSingleTop = true
                    restoreState = true
                }
                })
        }
    ) { innerPadding ->
        navigatorView(innerPadding)
    }
}