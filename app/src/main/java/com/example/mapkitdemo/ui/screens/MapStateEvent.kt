package com.example.mapkitdemo.ui.screens

import com.yandex.mapkit.ZoomRange
import com.yandex.mapkit.geometry.Point


data class MapState(
    val cameraPosition: Point = Point(55.751574, 37.573856), // Москва по умолчанию
    val zoom: Float = 10f,
    val lastPlacemark: Point? = null
)


sealed interface MapEvent {
    data class LongTap(val cords: Point) : MapEvent
    data class CameraSave(val point: Point, val zoom: Float) : MapEvent
    data class ConfirmPlacemark(val currentZoom: Int) : MapEvent
}
sealed interface MapEffect {
    data class Confirmed(val point: Point, val address: String) : MapEffect
}