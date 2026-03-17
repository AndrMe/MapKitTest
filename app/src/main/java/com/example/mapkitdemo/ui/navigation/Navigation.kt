package com.example.mapkitdemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.mapkitdemo.ui.screens.MapEffect
import com.example.mapkitdemo.ui.screens.MapScreen
import com.example.mapkitdemo.ui.screens.MapViewModel
import com.example.mapkitdemo.ui.screens.StartScreen
import com.yandex.mapkit.geometry.Point

sealed class MainRoute(val route: String) {
    object Start : MainRoute("start")
    object MapScreen : MainRoute("mapScreen")
}

sealed class MainGraphRoute(val route: String) {
    object Main : MainGraphRoute("main")
}

// --- Навигационный граф ---
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainGraphRoute.Main.route) {
        mainGraph(navController)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(startDestination = MainRoute.Start.route, route = MainGraphRoute.Main.route) {

        composable(MainRoute.Start.route) { backStackEntry ->

            // MutableState для Compose
            val pickedPointState = remember { mutableStateOf<Point?>(null) }
            val pickedAddressState = remember { mutableStateOf<String?>(null) }

            // Читаем результат из savedStateHandle при каждом recomposition
            LaunchedEffect(backStackEntry.savedStateHandle) {
                backStackEntry.savedStateHandle.get<Double>("selectedLat")?.let { lat ->
                    backStackEntry.savedStateHandle.get<Double>("selectedLon")?.let { lon ->
                        pickedPointState.value = Point(lat, lon)
                        backStackEntry.savedStateHandle.remove<Double>("selectedLat")
                        backStackEntry.savedStateHandle.remove<Double>("selectedLon")
                    }
                }
                backStackEntry.savedStateHandle.get<String>("selectedAddress")?.let { address ->
                    pickedAddressState.value = address
                    backStackEntry.savedStateHandle.remove<String>("selectedAddress")
                }
            }

            StartScreen(
                pickedPoint = pickedPointState.value,
                pickedAddress = pickedAddressState.value,
                onPick = { navController.navigate(MainRoute.MapScreen.route) },
            )
        }

        composable(MainRoute.MapScreen.route) {
            val viewModel: MapViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is MapEffect.Confirmed -> {
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