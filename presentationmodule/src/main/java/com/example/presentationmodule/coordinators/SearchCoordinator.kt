package com.example.presentationmodule.coordinators

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentationmodule.nestedfragment1.NestedFragment1
import com.example.presentationmodule.nestedfragment2.NestedFragment2
import com.example.presentationmodule.search.SearchFragment

@Composable
fun SearchCoordinator() {
    val searchNavController = rememberNavController()

    NavHost(
        navController = searchNavController,
        startDestination = SearchCoordinatorRoutes.SEARCH.value
    ) {
        composable(SearchCoordinatorRoutes.SEARCH.value) {
            val searchFragmentState = rememberFragmentState()
            AndroidFragment<SearchFragment>(
                modifier = Modifier.fillMaxSize(),
                fragmentState = searchFragmentState
            ) {
                it.navigateToNestedFragment1 = {
                    searchNavController.navigate("nestedFragment1") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }

        composable(SearchCoordinatorRoutes.NESTED_FRAGMENT_1.value) {
            AndroidFragment<NestedFragment1>(
                modifier = Modifier.fillMaxSize(),
            ) {
                it.navigateToNestedFragment2 = {
                    searchNavController.navigate("nestedFragment2") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }

        composable(SearchCoordinatorRoutes.NESTED_FRAGMENT_2.value) {
            AndroidFragment<NestedFragment2>(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

enum class SearchCoordinatorRoutes(val value: String) {
    SEARCH("search"),
    NESTED_FRAGMENT_1("nestedFragment1"),
    NESTED_FRAGMENT_2("nestedFragment2")
}

