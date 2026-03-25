package com.example.mapkitdemo.ui.map

import com.yandex.mapkit.geometry.Point


data class MapState(
    val cameraPosition: Point = Point(55.753089, 37.622651), // Москва по умолчанию
    val zoom: Float = 10f,
    val lastPlacemark: Point? = null,
    val placemarks: List<Point> = emptyList()
)
sealed interface MapEvent {
    data class LongTap(val cords: Point) : MapEvent
    data class CameraSave(val point: Point, val zoom: Float) : MapEvent
    data class ConfirmPlacemark(val currentZoom: Int) : MapEvent
}
sealed interface MapEffect {
    data class Confirmed(val point: Point, val address: String) : MapEffect
}