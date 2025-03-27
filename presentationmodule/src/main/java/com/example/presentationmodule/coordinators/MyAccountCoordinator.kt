package com.example.presentationmodule.coordinators

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentationmodule.MyAccountView

@Composable
fun MyAccountCoordinator() {
    val myAccountNavController = rememberNavController()

    NavHost(
        navController = myAccountNavController,
        startDestination = "myAccount"
    ) {
        composable("myAccount") { MyAccountView() }
    }
}