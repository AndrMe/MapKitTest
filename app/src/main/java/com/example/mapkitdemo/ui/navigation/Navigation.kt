package com.example.mapkitdemo.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.mapkitdemo.ui.screens.MapScreen
import com.example.mapkitdemo.ui.screens.ProceedScreen
import com.example.mapkitdemo.ui.screens.StartScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        mainGraph(navController)
    }
}

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(startDestination = "start", route = "main") {

        composable("start") { backStackEntry ->
            StartScreen(
                onPick = { navController.navigate("choose") },
                onProceed = { navController.navigate("proceed") }
            )
        }

        composable("mapScreen") {
            MapScreen(onPick = { navController.popBackStack() })
        }

        composable("proceed") {
            ProceedScreen(onBack = { navController.popBackStack() })
        }
    }
}