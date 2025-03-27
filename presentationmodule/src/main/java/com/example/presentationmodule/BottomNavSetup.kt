package com.example.presentationmodule

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.presentationmodule.coordinators.MainCoordinatorRoutes

@Composable
fun BottomNavSetup(navigateTo: (String) -> Unit) {
    val items = listOf(
        BottomNavigationItem("Search", Icons.Filled.Search, Icons.Outlined.Search, MainCoordinatorRoutes.SEARCH_COORDINATOR),
        BottomNavigationItem("Bookings", Icons.Filled.Email, Icons.Outlined.Email, MainCoordinatorRoutes.BOOKINGS_COORDINATOR),
        BottomNavigationItem("MyAccount", Icons.Filled.Settings, Icons.Outlined.Settings, MainCoordinatorRoutes.MY_ACCOUNT_COORDINATOR)
    )

    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navigateTo(item.route.value)
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

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: MainCoordinatorRoutes
)

