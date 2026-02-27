package alican.app.nomu.ui.Screen

import alican.app.nomu.data.model.RecipeDetail
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MealDetailScreen(
    detail: RecipeDetail,
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
                detail.materials.forEach {
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
