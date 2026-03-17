package com.example.mapkitdemo.ui.screens
import android.content.Context
import android.graphics.PointF
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import com.example.mapkitdemo.R
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.image.ImageProvider.fromResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    pickedPoint: Point? = null,
    pickedAddress: String? = null,
    onPick: () -> Unit,

) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Start") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = onPick) {
                Text("Выбрать на карте")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Показываем выбранную точку и адрес
            pickedPoint?.let { point ->
                Text("Выбранная точка: ${point.latitude}, ${point.longitude}")
                pickedAddress?.let { address ->
                    Text("Адрес: $address")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

        }
    }
}
