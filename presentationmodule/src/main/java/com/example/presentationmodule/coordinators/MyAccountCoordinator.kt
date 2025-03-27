package com.example.presentationmodule.coordinators

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentationmodule.myaccount.MyAccountView

@Composable
fun MyAccountCoordinator() {
    val myAccountNavController = rememberNavController()

    NavHost(
        navController = myAccountNavController,
        startDestination = MyAccountCoordinatorRoutes.MY_ACCOUNT.value
    ) {
        composable(MyAccountCoordinatorRoutes.MY_ACCOUNT.value) { MyAccountView() }
    }
}

enum class MyAccountCoordinatorRoutes(val value: String) {
    MY_ACCOUNT("myAccount")
}
