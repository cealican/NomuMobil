package alican.app.nomu

import alican.app.nomu.data.model.Screen
import alican.app.nomu.data.network.Service
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import alican.app.nomu.ui.Screen.NomuInputScreen
import alican.app.nomu.ui.Screen.SplashScreen
import alican.app.nomu.ui.ViewModel.HomeViewModel
import alican.app.nomu.util.SettingsManager
import alican.app.nomu.util.getStrings

@Composable
fun App() {
    MaterialTheme {
        // 1. Ayar Yöneticisini Başlat (Multiplatform Settings)
        val settingsManager = remember { SettingsManager() }

        // 2. Kayıtlı dili çek (Varsayılan yoksa "en")
        var selectedLang by remember { mutableStateOf(settingsManager.getLanguage() ?: "en") }

        // 3. Ekran ve Dil Durumunu Belirle
        var currentScreen by remember { mutableStateOf(Screen.Splash) }
        val strings = getStrings(selectedLang) // Seçili dile göre metinleri al
        val service = remember { Service() } // PHP sunucunuza bağlanacak olan servis
        val viewModel = remember { HomeViewModel(service) }
        Box(modifier = Modifier.fillMaxSize()) {
            when (currentScreen) {
                Screen.Splash -> {
                    SplashScreen(onTimeout = { currentScreen = Screen.Input })
                }
                Screen.Input -> {
                    input ekranı görselleri elden geçecek görünüm iyi ancak işlevsel eksikler var.
                    NomuInputScreen(
                        viewModel = viewModel,
                        strings = strings,
                        onSearchTriggered = { ingredients, location, category ->
                            // Burada PHP API'ye istek atılacak ve
                            // gelen verilerle RecipeList ekranına geçilecek.
                            // Şimdilik log basalım veya geçiş state'ini hazırla:
                            println("Arama Başladı: $ingredients, $location, $category")
                            // currentScreen = Screen.RecipeList
                        }
                    )
                    /*
                    InputScreenE(
                        strings = strings,
                        currentLang = selectedLang,
                        onLangChange = { newLang ->
                            selectedLang = newLang
                            settingsManager.setLanguage(newLang) // Kalıcı olarak kaydet
                        }
                    )*/
                }
            }
        }
    }
}

/* eski kod
@Composable
fun App() {
    val settingsManager = remember { SettingsManager() }
    var currentLang by remember { mutableStateOf(settingsManager.getLanguage()) }
    val strings = getStrings(currentLang)

    MaterialTheme {
        // Basitlik için ViewModel'i burada instantiate ediyoruz.
        // Gerçek projede Koin kullanılarak inject edilmeli.
        val viewModel = remember { HomeViewModel() }
        val state by viewModel.uiState.collectAsState()
        Column {
            // Dil Seçim Butonu (TopAppBar içinde veya üstte)
            LanguagePicker(currentLang) { newLang ->
                currentLang = newLang
                settingsManager.setLanguage(newLang)
            }
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                when (val currentState = state) {
                    is UiState.Idle -> InputScreen(
                        strings = strings, // Dil dosyasına göre metinleri gönder
                        onSearch = { ing, loc ->
                            viewModel.findRecipes(ing, loc, currentLang)
                        }
                    )

                    is UiState.Loading -> LoadingScreen()
                    is UiState.SuccessList -> MealListScreen(
                        loc = "Türkiye", // TODO: dışarıdan alınacak
                        recipes = currentState.data.recipes,
                        onRecipeClick = { name, loc -> viewModel.getRecipeDetails(name, loc, currentLang) },
                        onBack = { viewModel.resetToHome() }
                    )

                    is UiState.SuccessDetail -> MealDetailScreen(
                        detail = currentState.data,
                        onBack = {
                            // Basitlik için şimdilik ana ekrana dönüyoruz,
                            // listeye dönmek için state history tutulmalı.
                            viewModel.resetToHome()
                        }
                    )

                    is UiState.Error -> ErrorScreen(
                        msg = currentState.message,
                        onRetry = { viewModel.resetToHome() }
                    )
                }
            }
        }
    }
}
*/