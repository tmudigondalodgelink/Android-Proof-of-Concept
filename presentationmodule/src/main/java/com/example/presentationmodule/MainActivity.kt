package com.example.presentationmodule

import NavGraph
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
import androidx.compose.runtime.Composable
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.domainmodule.usecases.IUserAuthenticatedUseCase
import com.example.domainmodule.usecases.UserAuthenticatedUseCase
import com.example.presentationmodule.BookingsFragment
import com.example.presentationmodule.MyAccountView
import com.example.presentationmodule.MyAccountViewModel
import com.example.presentationmodule.NestedFragment1
import com.example.presentationmodule.NestedFragment2
import com.example.presentationmodule.NestedFragment4
import com.example.presentationmodule.NestedView3
import com.example.presentationmodule.PropertyDetailsView
import com.example.presentationmodule.SearchFragment
import com.example.presentationmodule.signin.SignInView
import com.example.presentationmodule.theme.ArchitecturePOC2Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var authenticatedUseCase: IUserAuthenticatedUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchitecturePOC2Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val noBottomNavScreens = setOf("signIn")
    val hideBottomNav = navController
        .currentBackStackEntryAsState().value?.destination?.route in noBottomNavScreens

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (!hideBottomNav) {
                BottomNavSetup(navigateTo = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
        }
    ) { innerPadding ->
        NavGraph(navController = navController, innerPadding = innerPadding)
    }
}



data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    val route: String
        get() {
            return title.lowercase()
        }
}

