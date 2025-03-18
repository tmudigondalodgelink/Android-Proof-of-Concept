package com.example.presentationmodule

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
import androidx.fragment.app.activityViewModels

class BookingsFragment: Fragment() {
    private val viewModel: BookingsViewModel by activityViewModels()
    var navigateToPropertyDetails: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                setContent {
                    CounterScreen(viewModel)
                }
            }
        }
    }

    @Composable
    fun CounterScreen(viewModel: BookingsViewModel) {
        val counter by viewModel.counter.collectAsState()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Bookings Fragment")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Counter: $counter", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.increment() }) {
                Text(text = "Increase")
            }
            Button(onClick = { navigateToPropertyDetails() }) {
                Text(text = "Navigate to property details")
            }
        }
    }
}
