package alican.app.nomu.ui.Screen

import alican.app.nomu.ui.Component.NomuTextField
import alican.app.nomu.ui.ViewModel.HomeViewModel
import alican.app.nomu.util.AppStrings
import alican.app.nomu.util.NomuColor.Companion.NomuBackground
import alican.app.nomu.util.NomuColor.Companion.NomuPurple
import alican.app.nomu.util.NomuColor.Companion.NomuSurface
import alican.app.nomu.util.NomuColor.Companion.NomuTextMain
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nomu.composeapp.generated.resources.Res
import nomu.composeapp.generated.resources.splash_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun NomuInputScreen(
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

        // Kategori Dropdown
        var categoryExpanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            NomuTextField(
                value = viewModel.selectedCategory,
                onValueChange = {},
                label = "Kategori başlık", //strings.categoryLabel,
                leadingIcon = Icons.Default.Menu,
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { categoryExpanded = true }) {
                        Icon(Icons.Default.ArrowDropDown, null)
                    }
                }
            )
            DropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false },
                modifier = Modifier.fillMaxWidth(0.8f).background(NomuBackground)
            ) {
                viewModel.categories.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat) },
                        onClick = {
                            viewModel.selectedCategory = cat
                            categoryExpanded = false
                        }
                    )
                }
            }
        }

        // Konum
        NomuTextField(
            value = viewModel.selectedLocation,
            onValueChange = { viewModel.onLocationChange(it) },
            label = strings.locationLabel,
            leadingIcon = Icons.Default.LocationOn
        )

        // Malzeme Input
        var ingredientInput by remember { mutableStateOf("") }
        NomuTextField(
            value = ingredientInput,
            onValueChange = {
                ingredientInput = it
                viewModel.onIngredientChange(it)
            },
            label = strings.ingredientsLabel,
            leadingIcon = Icons.Default.ShoppingCart,
            trailingIcon = {
                if (ingredientInput.isNotEmpty()) {
                    IconButton(onClick = {
                        viewModel.addIngredient(ingredientInput)
                        ingredientInput = ""
                    }) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = NomuPurple)
                    }
                }
            }
        )

        // Malzeme Kartları
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

        // M3 Buton Yapısı
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