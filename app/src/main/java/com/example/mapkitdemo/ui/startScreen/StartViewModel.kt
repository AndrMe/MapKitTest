package com.example.mapkitdemo.ui.startScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StartViewModel : ViewModel() {
    private val _state = MutableStateFlow(StartState())
    val state: StateFlow<StartState> = _state

    private val _effect = MutableSharedFlow<StartEffect>()
    val effect: SharedFlow<StartEffect> = _effect

    fun reducer(event: StartEvent) {
        when (event) {
            is StartEvent.GotAddress -> {
                if (event.lat != null && event.lon != null && event.address != null) {
                    _state.update { currentState ->
                        currentState.copy(
                            points = currentState.points + AddressPoint(
                                address = event.address,
                                latitude = event.lat,
                                longitude = event.lon
                            )
                        )
                    }
                }
            }
            is StartEvent.RemovePoint -> {
                _state.update { currentState ->
                    // Удаляем по индексу, проверяем, что индекс в пределах
                    val newPoints = currentState.points.toMutableList()
                    if (event.index in newPoints.indices) {
                        newPoints.removeAt(event.index)
                    }
                    currentState.copy(points = newPoints)
                }
            }
        }
    }
}