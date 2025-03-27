package com.example.presentationmodule.coordinators

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentationmodule.bookings.BookingsFragment
import com.example.presentationmodule.nestedfragment4.NestedFragment4
import com.example.presentationmodule.nestedview3.NestedView3
import com.example.presentationmodule.propertydetails.PropertyDetailsView

@Composable
fun BookingsCoordinator() {
    val bookingsNavController = rememberNavController()

    NavHost(
        navController = bookingsNavController,
        startDestination = "bookings"
    ) {

        composable("bookings") {
            val bookingFragmentState = rememberFragmentState()
            AndroidFragment<BookingsFragment>(
                modifier = Modifier.fillMaxSize(),
                fragmentState = bookingFragmentState,
            ) {
                it.navigateToPropertyDetails = {
                    bookingsNavController.navigate("propertyDetails") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }

        composable("propertyDetails") { PropertyDetailsView(navigateToNestedView3 = {
            bookingsNavController.navigate("nestedView3") {
                launchSingleTop = true
                restoreState = true
            }
        }) }


        composable("nestedView3") { NestedView3(navigateToNestedFragment4 = {
            bookingsNavController.navigate("nestedFragment4") {
                launchSingleTop = true
                restoreState = true
            }
        }) }

        composable("nestedFragment4") {
            AndroidFragment<NestedFragment4>(
                modifier = Modifier.fillMaxSize(),
            ) }
    }
}
