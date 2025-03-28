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
            startDestination = MainCoordinatorRoutes.SEARCH_COORDINATOR.value,
            modifier = Modifier.padding(paddings)
        ) {
            composable(MainCoordinatorRoutes.SEARCH_COORDINATOR.value) { SearchCoordinator() }
            composable(MainCoordinatorRoutes.BOOKINGS_COORDINATOR.value) { BookingsCoordinator() }
            composable(MainCoordinatorRoutes.MY_ACCOUNT_COORDINATOR.value) { MyAccountCoordinator() }
        }
    })
}

enum class MainCoordinatorRoutes(val value: String) {
    SEARCH_COORDINATOR("searchCoordinator"),
    BOOKINGS_COORDINATOR("bookingsCoordinator"),
    MY_ACCOUNT_COORDINATOR("myAccountCoordinator")
}
