package com.example.presentationmodule

import ApplicationCoordinator
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.example.domainmodule.models.Authentication
import com.example.domainmodule.usecases.IUserAuthenticatedUseCase
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
                MainScreen(authenticatedUseCase)
            }
        }
    }
}

@Composable
fun MainScreen(authenticatedUseCase: IUserAuthenticatedUseCase) {
    val navController = rememberNavController()

    val authenticationState by authenticatedUseCase.execute()
        .collectAsState(initial = Authentication.Unauthenticated)

    val isAuthenticated = authenticationState is Authentication.Authenticated

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            when (authenticationState) {
                is Authentication.Authenticated -> {
                    BottomNavSetup(
                        navigateTo = { route -> navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
                }
                is Authentication.Unauthenticated -> {
                    SignInView()
                }
            }
        }
    ) { innerPadding ->
        ApplicationCoordinator(navController, innerPadding)
    }
}
