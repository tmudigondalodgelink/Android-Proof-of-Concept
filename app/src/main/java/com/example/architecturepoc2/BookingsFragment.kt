package com.example.architecturepoc2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController

class BookingsFragment : Fragment() {

    var navigateToPropertyDetails: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                CounterScreen()
            }
        }
    }

    @Composable
    fun CounterScreen() {
        var counter by remember { mutableStateOf(0) }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bookings Fragment")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Counter: $counter", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { counter++ }) {
                Text(text = "Increase")
            }
            Button(onClick = { navigateToPropertyDetails() }) {
                Text(text = "Navigate to property details")
            }
        }
    }
}
