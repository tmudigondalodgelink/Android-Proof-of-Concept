package com.example.presentationmodule.signin

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignInView(
    viewModel: ISignInViewModel = hiltViewModel<SignInViewModel>(),
    navigateOnSuccess: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val email by viewModel.email.collectAsState()
            val password by viewModel.password.collectAsState()

            LaunchedEffect(viewModel.onSignInSuccess) {
                viewModel.onSignInSuccess.collect {
                    if (it) {
                        navigateOnSuccess()
                    }
                }
            }

            TextField(
                value = email,
                onValueChange = {
                    viewModel.setEmail(it)
                },
                placeholder = {
                    Text(
                        text = "Email"
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))

            )

            TextField(
                value = password,
                onValueChange = {
                    viewModel.setPassword(it)
                },
                placeholder = {
                    Text(
                        text = "Password"
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))

            )

            Button(
                onClick = { viewModel.singIn() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Sign In")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInView_Preview() {
    SignInView(SignInViewModel.fake(), {})
}