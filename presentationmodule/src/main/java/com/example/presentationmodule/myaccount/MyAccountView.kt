package com.example.presentationmodule.myaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun MyAccountView(viewModel: IMyAccountViewModel = hiltViewModel<MyAccountViewModel>()) {
    val user by viewModel.user.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD0BCFF)),
        contentAlignment = Alignment.Center
    ) {
        ComposableLifecycle { source, event ->
            when (event) {
                Lifecycle.Event.ON_START -> { viewModel.getMe() }
                else -> {}
            }
        }

        Column (
            modifier = Modifier.fillMaxSize()
                .padding(vertical = 56.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterVertically) ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            user?.let{
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${it.firstName.value} ${it.lastName.value}",
                        fontSize = 18.sp
                    )

                    Text(
                        text = it.email.value,
                        fontSize = 18.sp)
                }
            }

            Button(
                onClick = { viewModel.signOut() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Sign out")
            }
        }
    }
}

@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current,
    onEvent:(LifecycleOwner, Lifecycle.Event) ->Unit
) {
    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver{ source,event->
            onEvent(source,event)
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}