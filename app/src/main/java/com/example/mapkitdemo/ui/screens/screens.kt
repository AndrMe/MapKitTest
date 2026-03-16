package com.example.mapkitdemo.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun StartScreen(onPick: () -> Unit, onProceed: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Start") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onPick) { Text("Open Choose") }
            Button(onClick = onProceed) { Text("Open Proceed") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(onPick: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Choose") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("MapScreen  (empty)")
            Button(onClick = onPick) { Text("Back") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProceedScreen(onBack: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Proceed") }) }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Proceed screen (empty)")
            Button(onClick = onBack) { Text("Back") }
        }
    }
}