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
        startDestination = BookingsCoordinatorRoutes.BOOKINGS.value
    ) {

        composable(BookingsCoordinatorRoutes.BOOKINGS.value) {
            val bookingFragmentState = rememberFragmentState()
            AndroidFragment<BookingsFragment>(
                modifier = Modifier.fillMaxSize(),
                fragmentState = bookingFragmentState,
            ) {
                it.navigateToPropertyDetails = {
                    bookingsNavController.navigate(BookingsCoordinatorRoutes.PROPERTY_DETAILS.value) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }

        composable(BookingsCoordinatorRoutes.PROPERTY_DETAILS.value) { PropertyDetailsView(navigateToNestedView3 = {
            bookingsNavController.navigate(BookingsCoordinatorRoutes.NESTED_VIEW_3.value) {
                launchSingleTop = true
                restoreState = true
            }
        }) }


        composable(BookingsCoordinatorRoutes.NESTED_VIEW_3.value) { NestedView3(navigateToNestedFragment4 = {
            bookingsNavController.navigate(BookingsCoordinatorRoutes.NESTED_FRAGMENT_4.value) {
                launchSingleTop = true
                restoreState = true
            }
        }) }

        composable(BookingsCoordinatorRoutes.NESTED_FRAGMENT_4.value) {
            AndroidFragment<NestedFragment4>(
                modifier = Modifier.fillMaxSize(),
            ) }
    }
}

enum class BookingsCoordinatorRoutes(val value: String) {
    BOOKINGS("bookings"),
    PROPERTY_DETAILS("propertyDetails"),
    NESTED_VIEW_3("nestedView3"),
    NESTED_FRAGMENT_4("nestedFragment4")
}
