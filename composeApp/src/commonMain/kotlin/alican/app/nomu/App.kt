package alican.app.nomu

import alican.app.nomu.data.model.Screen
import alican.app.nomu.data.network.Service
import alican.app.nomu.ui.Screen.InputScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import alican.app.nomu.ui.Screen.SplashScreen
import alican.app.nomu.ui.ViewModel.HomeViewModel

@Composable
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screen.Splash) }
        val service = remember { Service() }
        val viewModel = remember { HomeViewModel(service) }
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentScreen) {
                Screen.Splash -> {
                    SplashScreen(onTimeout = { currentScreen = Screen.Input })
                }
                Screen.Input -> {
                    InputScreen(
                        viewModel = viewModel,
                        onSearchTriggered = { ingredients, location, category ->
                            println("Arama Başladı: $ingredients, $location, $category")
                            // TODO arama işlemleri
                        }
                    )
                }

                Screen.MealList -> TODO()
                Screen.MealDetail -> TODO()
            }
        }
    }
}