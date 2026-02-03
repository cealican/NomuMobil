package alican.app.nomu.ui.Screen

import alican.app.nomu.ui.Component.AutocompleteField
import alican.app.nomu.ui.Component.CategoryDropdown
import alican.app.nomu.ui.ViewModel.HomeViewModel
import alican.app.nomu.util.AppStrings
import alican.app.nomu.util.NomuColor.Companion.NomuBackground
import alican.app.nomu.util.NomuColor.Companion.NomuPurple
import alican.app.nomu.util.NomuColor.Companion.NomuSurface
import alican.app.nomu.util.NomuColor.Companion.NomuTextMain
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nomu.composeapp.generated.resources.Res
import nomu.composeapp.generated.resources.splash_logo
import org.jetbrains.compose.resources.painterResource


@Composable
fun InputScreen(
    viewModel: HomeViewModel,
    strings: AppStrings,
    onSearchTriggered: (ingredients: List<String>, location: String, category: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NomuBackground)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Logo
        Image(
            painter = painterResource(Res.drawable.splash_logo),
            contentDescription = "Nomu Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 1. KATEGORİ (Dropdown)
        CategoryDropdown(
            label = "Kategori alanı", //strings.categoryLabel,
            options = viewModel.categories,
            selectedOption = viewModel.selectedCategory,
            onOptionSelected = { viewModel.selectedCategory = it }
        )

        // 2. KONUM (Autocomplete - 3 karakter sonra öneri)
        AutocompleteField(
            label = strings.locationLabel,
            value = viewModel.selectedLocation,
            suggestions = viewModel.locationSuggestions,
            leadingIcon = Icons.Default.LocationOn,
            onValueChange = { viewModel.onLocationChange(it) },
            onSuggestionSelected = { viewModel.selectedLocation = it }
        )

        // 3. MALZEMELER (Autocomplete - Seçilince alttaki listeye ekler)
        var ingredientSearchText by remember { mutableStateOf("") }
        AutocompleteField(
            label = strings.ingredientsLabel,
            value = ingredientSearchText,
            suggestions = viewModel.ingredientSuggestions,
            leadingIcon = Icons.Default.ShoppingCart,
            onValueChange = {
                ingredientSearchText = it
                viewModel.onIngredientChange(it)
            },
            onSuggestionSelected = { selected ->
                viewModel.addIngredient(selected)
                ingredientSearchText = "" // Seçim sonrası arama alanını temizle
            }
        )

        // SEÇİLEN MALZEMELER LİSTESİ (Tasarımı korundu)
        Column(modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) {
            viewModel.selectedIngredients.forEach { ingredient ->
                Card(
                    modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = NomuSurface)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(ingredient, color = NomuTextMain, style = MaterialTheme.typography.bodyMedium)
                        IconButton(onClick = { viewModel.removeIngredient(ingredient) }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // TARİF BUL BUTONU
        Button(
            onClick = {
                onSearchTriggered(
                    viewModel.selectedIngredients.toList(),
                    viewModel.selectedLocation,
                    viewModel.selectedCategory
                )
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NomuPurple),
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(10.dp))
            Text(strings.findButton, color = Color.White, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

/*
@Composable
fun InputScreen(viewModel: HomeViewModel, strings: AppStrings, onSearchTriggered: (ingredients: List<String>, location: String, category: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // --- Kategori Dropdown ---
        CategoryDropdown(
            label = "Kategori başlığı", //    strings.categoryLabel, // AppStrings'ten geliyor
            options = viewModel.categories,
            selectedOption = viewModel.selectedCategory,
            onOptionSelected = { viewModel.selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Bölge Autocomplete ---
        AutocompleteTextField(
            label = strings.locationLabel,
            value = viewModel.selectedLocation,
            suggestions = viewModel.locationSuggestions,
            onValueChange = { viewModel.onLocationChange(it) },
            onSuggestionSelected = { viewModel.selectedLocation = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- Malzeme Arama ---
        var tempIngredientQuery by remember { mutableStateOf("") }
        AutocompleteTextField(
            label = strings.ingredientsLabel,
            value = tempIngredientQuery,
            suggestions = viewModel.ingredientSuggestions,
            onValueChange = {
                tempIngredientQuery = it
                viewModel.onIngredientChange(it)
            },
            onSuggestionSelected = {
                viewModel.addIngredient(it)
                tempIngredientQuery = "" // Seçimden sonra kutuyu temizle
            }
        )

        // --- Seçilen Malzemeler Listesi (Gri Kutu) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(Color(0xFFD9D9D9))
                .border(1.dp, Color.Black)
                .heightIn(min = 200.dp)
        ) {
            Column {
                viewModel.selectedIngredients.forEach { item ->
                    IngredientRow(
                        name = item,
                        onDelete = { viewModel.removeIngredient(item) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- TARİF BUL BUTONU (Tetikleyici) ---
        Button(
            onClick = {
                // Güvenlik kontrolü: En az bir malzeme seçilmiş mi?
                if (viewModel.selectedIngredients.isNotEmpty()) {
                    onSearchTriggered(
                        viewModel.selectedIngredients.toList(),
                        viewModel.selectedLocation,
                        viewModel.selectedCategory
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D4037)),
            shape = RectangleShape,
            enabled = viewModel.selectedIngredients.isNotEmpty() // Malzeme yoksa butonu pasif yapabilirsin
        ) {
            Text(
                text = strings.findButton,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}*/
/*
private val HomeViewModel.selectedCategory: String
private val HomeViewModel.selectedLocation: String
private val HomeViewModel.selectedIngredients: Any

@Composable
fun InputScreen(
    viewModel: HomeViewModel,
    strings: AppStrings,
    onSearchTriggered: (ingredients: List<String>, location: String, category: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        // 1. KATEGORİ (Dropdown)
        Text("Kategori seç", style = MaterialTheme.typography.labelLarge)
        CategoryDropdown(
            options = viewModel.categories,
            selectedOption = viewModel.selectedCategory,
            onOptionSelected = { viewModel.selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 2. BÖLGE (Autocomplete)
        Text(strings.locationLabel, style = MaterialTheme.typography.labelLarge)
        AutocompleteTextField(
            value = viewModel.selectedLocation,
            suggestions = viewModel.locationSuggestions,
            onValueChange = { viewModel.onLocationChange(it) },
            onSuggestionSelected = { viewModel.selectedLocation = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. MALZEMELER (Autocomplete + List)
        Text(strings.ingredientsLabel, style = MaterialTheme.typography.labelLarge)
        AutocompleteTextField(
            value = "", // Arama kutusu seçilince temizlenmeli
            suggestions = viewModel.ingredientSuggestions,
            onValueChange = { viewModel.onIngredientChange(it) },
            onSuggestionSelected = { viewModel.addIngredient(it) }
        )

        // Seçilen Malzemeler Listesi (Mockup'taki kutu yapısı)
        Surface(
            modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp).padding(vertical = 12.dp),
            border = BorderStroke(1.dp, Color.Black),
            color = Color(0xFFE0E0E0) // Mockup'taki gri ton
        ) {
            Column {
                viewModel.selectedIngredients.forEach { ingredient ->
                    IngredientRow(
                        name = ingredient,
                        onDelete = { viewModel.removeIngredient(ingredient) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 4. TARİF BUL BUTONU
        Button(
            onClick = {
                onSearchTriggered(
                    viewModel.selectedIngredients.toList(),
                    viewModel.selectedLocation,
                    viewModel.selectedCategory
                )
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF634242)), // Mockup kahverengi tonu
            shape = RectangleShape
        ) {
            Text(strings.findButton, color = Color.White)
        }
    }
}*/

@Composable
fun IngredientRow(name: String, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp).background(Color.White).border(1.dp, Color.Black),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, modifier = Modifier.padding(start = 12.dp))
        Button(
            onClick = onDelete,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)), // Sil butonu kırmızısı
            shape = RectangleShape,
            modifier = Modifier.padding(4.dp)
        ) {
            Text("Sil", fontSize = 12.sp)
        }
    }
}

/*
@Composable
fun InputScreenE(strings: AppStrings,
                currentLang: String,
                onLangChange: (String) -> Unit/*
    strings: AppStrings, // Dil desteği için ekledik
    onSearch: (String, String) -> Unit*/
) {
    var ingredients by remember { mutableStateOf("Kıyma, Domates, Patlıcan") } // TODO: temizlenecek
    var location by remember { mutableStateOf("Türkiye") } // TODO: temizlenecek

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
                   // todo onSearch(ingredients, location)
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
}*/