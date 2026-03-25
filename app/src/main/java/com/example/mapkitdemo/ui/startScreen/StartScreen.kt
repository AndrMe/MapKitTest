package com.example.mapkitdemo.ui.startScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StartScreen(
    state: StartState,
    onEvent: (StartEvent) -> Unit,
    onPick: () -> Unit
) {
    StartScreenContent(
        points = state.points,
        onPick = onPick,
        onRemove = { index -> onEvent(StartEvent.RemovePoint(index)) }
    )
}

@Composable
private fun StartScreenContent(
    points: List<AddressPoint>,
    onPick: () -> Unit,
    onRemove: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = onPick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Выбрать новую точку")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (points.isEmpty()) {
            Text("Нет выбранных точек")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(points) { index, point ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = point.address,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { onRemove(index) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Удалить"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}