package alican.app.nomu.ui.Screen

import alican.app.nomu.ui.Component.AutocompleteField
import alican.app.nomu.ui.Component.LanguagePicker
import alican.app.nomu.ui.ViewModel.HomeViewModel
import alican.app.nomu.util.NomuColor.Companion.NomuBackground
import alican.app.nomu.util.NomuColor.Companion.NomuPurple
import alican.app.nomu.util.NomuColor.Companion.NomuSurface
import alican.app.nomu.util.NomuColor.Companion.NomuTextMain
import alican.app.nomu.util.SettingsManager
import alican.app.nomu.util.getStrings
import alican.app.nomu.util.selectedLanguageId
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    viewModel: HomeViewModel,
    onSearchTriggered: (materials: List<String>, location: String, category: String) -> Unit
) {
    val settingsManager = remember { SettingsManager() }
    var currentLang by remember { mutableStateOf(settingsManager.getLanguage()) }
    val strings = getStrings(currentLang)

    // Bottom Sheet kontrolü için state
    val sheetState = rememberModalBottomSheetState()
    var showMaterialPicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NomuBackground)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row {
            Spacer(modifier = Modifier.weight(1f))
            LanguagePicker(selectedLanguageId) { newLang ->
                currentLang = newLang
                settingsManager.setLanguage(newLang)
                viewModel.getAllMaterialsWithCategory(selectedLanguageId)
                viewModel.clearMateriallist()
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(Res.drawable.splash_logo),
            contentDescription = "",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        AutocompleteField(
            label = strings.locationLabel,
            value = viewModel.selectedLocation,
            suggestions = viewModel.locationSuggestions,
            leadingIcon = Icons.Default.LocationOn,
            onValueChange = { viewModel.onLocationChange(it) },
            onSuggestionSelected = { viewModel.selectedLocation = it }
        )

        // Malzeme seç butonu
        OutlinedButton(
            onClick = { showMaterialPicker = true },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = NomuTextMain),
            border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f))
        ) {
            Icon(Icons.Default.RestaurantMenu, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(strings.materialsLabel) // "Malzeme Seç" veya mevcut label
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.Add, contentDescription = null)
        }
/*
        // Malzeme Input
        var materialSearchText by remember { mutableStateOf("") }
        AutocompleteField(
            label = strings.materialsLabel,
            value = materialSearchText,
            suggestions = viewModel.materialSuggestions,
            leadingIcon = Icons.Default.RestaurantMenu,
            onValueChange = {
                materialSearchText = it
                viewModel.onMaterialChange(it)
            },
            onSuggestionSelected = { selected ->
                // todo viewModel.addMaterial(selected)
                materialSearchText = "" // Seçim sonrası arama alanını temizle
            }
        )*/

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.selectedMaterials) { material ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = NomuSurface)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = material.name,
                            color = NomuTextMain,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { viewModel.removeMaterial(material) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
               /* todo var message = ""

                if(viewModel.selectedCategory == null) {
                    message += "Kategori seçmelisiniz"
                }

                onSearchTriggered(
                    viewModel.selectedMaterials.toList(),
                    viewModel.selectedLocation,
                    viewModel.selectedCategory
                )*/
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

        Spacer(modifier = Modifier.height(50.dp))
        // --- MALZEME SEÇİM MODALI ---
        if (showMaterialPicker) {
            ModalBottomSheet(
                onDismissRequest = { showMaterialPicker = false },
                sheetState = sheetState,
                containerColor = NomuSurface,
                dragHandle = { BottomSheetDefaults.DragHandle(color = Color.Gray) }
            ) {
                MaterialPickerContent(
                    viewModel = viewModel,
                    onDismiss = { showMaterialPicker = false }
                )
            }
        }
    }
}

@Composable
fun MaterialPickerContent(
    viewModel: HomeViewModel,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .padding(16.dp)
    ) {
        Text(
            "Malzeme Seçin",
            style = MaterialTheme.typography.titleLarge,
            color = NomuTextMain,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            viewModel.categoriesWithMaterials.forEach { category ->

                item(key = "cat_${category.id}") {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.labelLarge,
                        color = NomuPurple,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }

                items(
                    items = category.materialList,
                    key = { material -> material.id }
                ) { material ->
                    val isSelected = viewModel.selectedMaterials.contains(material)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.toggleMaterial(material)
                            }
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { viewModel.toggleMaterial(material) },
                            colors = CheckboxDefaults.colors(checkedColor = NomuPurple)
                        )
                        Text(
                            text = material.name,
                            color = NomuTextMain,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = NomuPurple)
        ) {
            Text("Tamam")
        }
    }
}