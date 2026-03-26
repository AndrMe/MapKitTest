package com.example.mapkitdemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.mapkitdemo.ui.map.MapEffect
import com.example.mapkitdemo.ui.map.MapScreen
import com.example.mapkitdemo.ui.map.MapViewModel
import com.example.mapkitdemo.ui.startScreen.AddressPoint
import com.example.mapkitdemo.ui.startScreen.StartEvent
import com.example.mapkitdemo.ui.startScreen.StartScreen
import com.example.mapkitdemo.ui.startScreen.StartViewModel
import kotlinx.serialization.Serializable

@Serializable
object MainGraph

@Serializable
object Start

@Serializable
data class MapScreenRoute(
    val addresses: List<String>,
    val latitudes: List<Double>,
    val longitudes: List<Double>
)

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainGraph
    ) {
        mainGraph(navController)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation<MainGraph>(startDestination = Start) {

        composable<Start> { backStackEntry ->
            val viewModel: StartViewModel = viewModel()
            val uiState by viewModel.state.collectAsStateWithLifecycle()

            // При нажатии на кнопку формируем маршрут с текущими точками
            val onPick = {
                val points = uiState.points
                val addresses = points.map { it.address }
                val latitudes = points.map { it.latitude }
                val longitudes = points.map { it.longitude }
                navController.navigate(MapScreenRoute(addresses, latitudes, longitudes))
            }

            // Получаем новую точку с карты (возврат через savedStateHandle)
            LaunchedEffect(backStackEntry) {
                snapshotFlow {
                    Triple(
                        backStackEntry.savedStateHandle.get<Double>("selectedLat"),
                        backStackEntry.savedStateHandle.get<Double>("selectedLon"),
                        backStackEntry.savedStateHandle.get<String>("selectedAddress")
                    )
                }.collect { (lat, lon, address) ->
                    if (lat != null && lon != null && address != null) {
                        viewModel.reducer(StartEvent.GotAddress(lat, lon, address))
                        backStackEntry.savedStateHandle.remove<Double>("selectedLat")
                        backStackEntry.savedStateHandle.remove<Double>("selectedLon")
                        backStackEntry.savedStateHandle.remove<String>("selectedAddress")
                    }
                }
            }

            StartScreen(
                state = uiState,
                onEvent = viewModel::reducer,
                onPick = onPick
            )
        }

        composable<MapScreenRoute> { backStackEntry ->
            // Получаем переданные списки из аргументов маршрута
            val route = backStackEntry.toRoute<MapScreenRoute>()
            val points = route.addresses.indices.map { index ->
                AddressPoint(
                    address = route.addresses[index],
                    latitude = route.latitudes[index],
                    longitude = route.longitudes[index]
                )
            }

            val viewModel: MapViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            // Устанавливаем начальные точки на карте
            LaunchedEffect(points) {
                viewModel.setInitialPoints(points)
            }

            // Обрабатываем эффект подтверждения (возврат новой точки)
            LaunchedEffect(Unit) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is MapEffect.Chosen -> {
                            navController.previousBackStackEntry?.savedStateHandle?.apply {
                                set("selectedLat", effect.point.latitude)
                                set("selectedLon", effect.point.longitude)
                                set("selectedAddress", effect.address)
                            }
                            navController.popBackStack()
                        }
                    }
                }
            }

            MapScreen(state, viewModel::reducer)
        }
    }
}