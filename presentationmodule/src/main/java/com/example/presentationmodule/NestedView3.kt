package com.example.presentationmodule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun NestedView3(viewModel: NestedView3ViewModel = hiltViewModel(), navController: NavController) {
    val counter by viewModel.counter.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFCC80)),
        contentAlignment = Alignment.Center
    ) {
        Column{
            Text(
                text = "Nested View 3",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(text = "Counter: $counter", style = MaterialTheme.typography.headlineMedium)

            Button(onClick = { viewModel.increment() }) {
                Text(text = "Increase")
            }

            Button(onClick = { navController.navigate("nestedFragment4") }) {
                Text(text = "Navigate to Nested Fragment 4")
            }
        }
    }
}

