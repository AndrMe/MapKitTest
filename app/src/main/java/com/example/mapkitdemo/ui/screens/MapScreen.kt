package com.example.mapkitdemo.ui.screens

import android.content.Context
import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mapkitdemo.R
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

@Composable
fun MapScreen(
    state: MapState,
    onEvent: (MapEvent) -> Unit
) {
    val context = LocalContext.current

    val mapView = remember {
        MapView(context)
    }



    DisposableEffect(Unit) {
        Log.d("recomp", "recomp")
        val map = mapView.mapWindow.map

            //Обработака нажатий, взаимодействия с кратой
        val inputListener = object : InputListener {
            override fun onMapTap(map: Map, point: Point) {}

            override fun onMapLongTap(map: Map, point: Point) {
                onEvent(MapEvent.LongTap(point))
            }
        }
        map.addInputListener(inputListener)
        mapView.onStart()
        onDispose {
            onEvent(
                MapEvent.CameraSave(
                    map.cameraPosition.target,
                    map.cameraPosition.zoom
                )
            )
            map.removeInputListener(inputListener)
            mapView.onStop()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { mapView },
            update = {}
        )
        LaunchedEffect(state.cameraPosition, state.zoom) {
            mapView.mapWindow.map.move(
                CameraPosition(
                    state.cameraPosition,
                    state.zoom,
                    0f,
                    0f
                )
            )
        }
        if (state.lastPlacemark != null) {
            FloatingActionButton(
                onClick = { onEvent(MapEvent.ConfirmPlacemark(mapView.mapWindow.map.cameraPosition.zoom.toInt())) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Подтвердить"
                )
            }
        }
        val mapObjects = mapView.mapWindow.map.mapObjects
        mapObjects.clear()

        state.lastPlacemark?.let {
            addMarker(mapObjects, it, context)
        }
    }

}

//Добавление маркеров
private fun addMarker(
    mapObjects: MapObjectCollection,
    point: Point,
    context: Context
): PlacemarkMapObject {
    mapObjects.clear()
    return mapObjects.addPlacemark().apply {
        geometry = point
        setIcon(ImageProvider.fromResource(context, R.drawable.location), IconStyle().apply {
            anchor = PointF(0.5f, 0.5f)
            flat = false
            scale = 0.1f
        })
    }

}