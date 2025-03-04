package com.example.architecturepoc2.View

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
import com.example.architecturepoc2.MyAccountViewModel
import com.example.architecturepoc2.PropertyDetailsViewModel

@Composable
fun PropertyDetailsView(viewModel: PropertyDetailsViewModel = hiltViewModel(), navController: NavController) {
    val counter by viewModel.counter.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFBBDEFB)),
        contentAlignment = Alignment.Center
    ) {
        Column{
            Text(
                text = "Property Details",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(text = "Counter: $counter", style = MaterialTheme.typography.headlineMedium)

            Button(onClick = { viewModel.increment() }) {
                Text(text = "Increase")
            }

            Button(onClick = { navController.navigate("nestedView2") }) {
                Text(text = "Navigate to Nested View 2")
            }
        }
    }
}

