package com.example.presentationmodule.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignInView(viewModel: ISignInViewModel = hiltViewModel<SignInViewModel>()) {
    Column(
        modifier = Modifier
    ) {
        val email by viewModel.email.collectAsState()
        val password by viewModel.password.collectAsState()

        TextField(value = email, onValueChange = {
            viewModel.setEmail(it)
        })

        TextField(value = password, onValueChange = {
            viewModel.setPassword(it)
        })

        Button(onClick = { viewModel.singIn() }) {
            Text("Sign In")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInView_Preview() {
    SignInView(SignInViewModel.fake())
}