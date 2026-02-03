package alican.app.nomu.ui.Screen

import alican.app.nomu.data.model.RecipeSummary
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun MealListScreen(
    loc: String,
    recipes: List<RecipeSummary>,
    onRecipeClick: (String, String) -> Unit,
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
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onRecipeClick(recipe.name, loc) },
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(recipe.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            SuggestionChip(onClick = {}, label = { Text(recipe.time) })
                            Spacer(modifier = Modifier.width(8.dp))
                            SuggestionChip(onClick = {}, label = { Text(recipe.difficulty) })
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        // Besin Değerleri Satırı
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            NutrientInfo(label = "🔥", value = recipe.calories)
                            NutrientInfo(label = "🥩", value = recipe.protein)
                            NutrientInfo(label = "🍞", value = recipe.carbs)
                        }
                    }
                }
            }
        }
    }
}