package com.example.presentationmodule.coordinators

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentationmodule.signin.SignInView
import com.example.presentationmodule.signin.SignInViewModel


@Composable
fun AuthenticationCoordinator(viewModel: SignInViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.resetState()
    }
    SignInView()
}