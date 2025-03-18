package com.example.architecturepoc2

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.fragment.compose.AndroidFragment
import androidx.fragment.compose.rememberFragmentState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentationmodule.BookingsFragment
import com.example.presentationmodule.MyAccountView
import com.example.presentationmodule.MyAccountViewModel
import com.example.presentationmodule.NestedFragment1
import com.example.presentationmodule.NestedFragment2
import com.example.presentationmodule.NestedFragment4
import com.example.presentationmodule.NestedView3
import com.example.presentationmodule.PropertyDetailsView
import com.example.presentationmodule.SearchFragment
import com.example.presentationmodule.theme.ArchitecturePOC2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchitecturePOC2Theme {
                val items = listOf(
                    BottomNavigationItem(
                        title = "Search",
                        selectedIcon = Icons.Filled.Search,
                        unselectedIcon = Icons.Outlined.Search,
                    ),
                    BottomNavigationItem(
                        title = "Bookings",
                        selectedIcon = Icons.Filled.Email,
                        unselectedIcon = Icons.Outlined.Email
                    ),
                    BottomNavigationItem(
                        title = "MyAccount",
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings
                    )

                )

                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                val navController = rememberNavController()

                val searchFragmentState = rememberFragmentState()
                val myAccountViewState = rememberFragmentState()


                val context = LocalContext.current

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        when (index) {
                                            0 -> navController.navigate("search") {
                                                launchSingleTop = true
                                                restoreState = true
                                            }

                                            1 -> navController.navigate("bookings") {
                                                launchSingleTop = true
                                                restoreState = true
                                            }

                                            2 -> navController.navigate("myAccount") {
                                                launchSingleTop = true
                                                restoreState = true
                                            }

                                            else -> navController.navigate("search")
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    },
                                    label = { Text(item.title) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    val myAccountViewModel: MyAccountViewModel = hiltViewModel()

                    NavHost(
                        navController = navController,
                        startDestination = "search",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("search") {
                            AndroidFragment<SearchFragment>(
                                modifier = Modifier.fillMaxSize(),
                                fragmentState = searchFragmentState
                            ) {
                                it.navigateToNestedFragment1 = {
                                    navController.navigate("nestedFragment1") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        }

                        composable("bookings") {
                            val bookingFragmentState = rememberFragmentState()
                            AndroidFragment<BookingsFragment>(
                                modifier = Modifier.fillMaxSize(),
                                fragmentState = bookingFragmentState,
                            ) {
                                it.navigateToPropertyDetails = {
                                    navController.navigate("propertyDetails") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        }
                        composable("myAccount") { MyAccountView(myAccountViewModel) }

                        composable("propertyDetails") { PropertyDetailsView(navController = navController) }

                        composable("nestedFragment1") {
                            AndroidFragment<NestedFragment1>(
                                modifier = Modifier.fillMaxSize(),
                            ) {
                                it.navigateToNestedFragment2 = {
                                    navController.navigate("nestedFragment2") {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            } }

                        composable("nestedFragment2") {
                            AndroidFragment<NestedFragment2>(
                                modifier = Modifier.fillMaxSize(),
                            ) }

                        composable("nestedView3") { NestedView3(navController = navController) }

                        composable("nestedFragment4") {
                            AndroidFragment<NestedFragment4>(
                                modifier = Modifier.fillMaxSize(),
                            ) }
                    }
                }
            }
        }
    }
}


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

