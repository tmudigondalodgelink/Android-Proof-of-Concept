package com.example.presentationmodule.coordinators

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentationmodule.MainScreen

@Composable
fun MainCoordinator() {
    val navController = rememberNavController()

    MainScreen(navController = navController, navigatorView = { paddings ->
        NavHost(
            navController = navController,
            startDestination = "searchCoordinator",
            modifier = Modifier.padding(paddings)
        ) {
            composable("searchCoordinator") { SearchCoordinator() }
            composable("bookingsCoordinator") { BookingsCoordinator() }
            composable("myAccountCoordinator") { MyAccountCoordinator() }
        }
    })
}
