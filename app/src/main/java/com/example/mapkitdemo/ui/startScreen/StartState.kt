package com.example.mapkitdemo.ui.startScreen

import com.yandex.mapkit.geometry.Point
import kotlinx.serialization.Serializable

@Serializable
data class AddressPoint(
    val address: String,
    val latitude: Double,
    val longitude: Double
)

@Serializable
data class MapScreenRoute(
    val addresses: List<String>,
    val latitudes: List<Double>,
    val longitudes: List<Double>
)

data class StartState(
    val points: List<AddressPoint> = emptyList()
)

sealed interface StartEvent {
    data class GotAddress(val lat: Double?, val lon: Double?, val address: String?) : StartEvent
    data class RemovePoint(val index: Int) : StartEvent   // используем индекс
}

sealed interface StartEffect