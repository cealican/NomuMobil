package alican.app.nomu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import alican.app.nomu.ui.HomeViewModel
import alican.app.nomu.ui.UiState
import alican.app.nomu.util.AppStrings
import alican.app.nomu.util.SettingsManager
import alican.app.nomu.util.getStrings
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.text.font.FontWeight

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
                            // ViewModel'e dili de parametre olarak geçiyoruz
                            viewModel.findRecipes(ing, loc, currentLang)
                        }
                    )

                    is UiState.Loading -> LoadingScreen()
                    is UiState.SuccessList -> RecipeListScreen(
                        recipes = currentState.data.recipes,
                        onRecipeClick = { name -> viewModel.getRecipeDetails(name, currentLang) },
                        onBack = { viewModel.resetToHome() }
                    )

                    is UiState.SuccessDetail -> RecipeDetailScreen(
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

// --- Alt Ekran Bileşenleri ---
@Composable
fun LanguagePicker(currentLang: String, onLangSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.TopEnd) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.Language, contentDescription = "Dil")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Türkçe 🇹🇷") },
                onClick = { onLangSelected("tr"); expanded = false }
            )
            DropdownMenuItem(
                text = { Text("English 🇺🇸") },
                onClick = { onLangSelected("en"); expanded = false }
            )
        }
    }
}
@Composable
fun InputScreen(
    strings: AppStrings, // Dil desteği için ekledik
    onSearch: (String, String) -> Unit
) {
    var ingredients by remember { mutableStateOf("Kıyma, Domates, Patlıcan") }
    var location by remember { mutableStateOf("Türkiye") }

    // Tasarımı merkezlemek ve şıklaştırmak için Column yapısı
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()), // Küçük ekranlarda kaydırma desteği
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Uygulama Logosu veya İkonu (Burada bir emoji veya logo ikonunu hayal edin)
        Text(
            text = "🍳",
            style = MaterialTheme.typography.displayLarge
        )

        Text(
            text = strings.title,
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Malzeme Giriş Alanı
        OutlinedTextField(
            value = ingredients,
            onValueChange = { ingredients = it },
            label = { Text(strings.ingredientsLabel) },
            placeholder = { Text("Tavuk, Patates, Soğan...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Default.RestaurantMenu, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Konum Giriş Alanı
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text(strings.locationLabel) },
            placeholder = { Text("İstanbul, Paris, Tokyo...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = { Icon(Icons.Default.Place, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Arama Butonu
        Button(
            onClick = {
                if (ingredients.isNotBlank()) {
                    onSearch(ingredients, location)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            enabled = ingredients.isNotBlank() // Malzeme boşsa buton pasif olur
        ) {
            Icon(Icons.Default.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = strings.findButton,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Küçük bir ipucu metni
        Text(
            text = "Nomu uses AI to find the best local recipes.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
@Composable
fun RecipeListScreen(
    recipes: List<alican.app.nomu.data.model.RecipeSummary>,
    onRecipeClick: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack) { Text("< Geri") }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Önerilen Yemekler", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(recipes) { recipe ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onRecipeClick(recipe.name) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(recipe.name, style = MaterialTheme.typography.titleMedium)
                        Text("Süre: ${recipe.time} • Zorluk: ${recipe.difficulty}")
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeDetailScreen(
    detail: alican.app.nomu.data.model.RecipeDetail,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBack) { Text("< Ana Menü") }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(detail.name, style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Malzemeler:", style = MaterialTheme.typography.titleMedium)
                detail.ingredients.forEach {
                    Text("• $it")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Yapılışı:", style = MaterialTheme.typography.titleMedium)
                detail.steps.forEach {
                    Text("- $it", modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
        Text("Nomu Şefi Düşünüyor...", modifier = Modifier.padding(top = 64.dp))
    }
}

@Composable
fun ErrorScreen(msg: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Hata Oluştu: $msg", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) { Text("Tekrar Dene") }
        }
    }
}