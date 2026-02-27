package alican.app.nomu.ui.Component

import alican.app.nomu.data.model.Language
import alican.app.nomu.util.languages
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LanguagePicker(currentLangId: Int, onLangSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val languageList = listOf(
        Language(1, "tr", "Türkçe 🇹🇷"),
        Language(2, "en", "English 🇺🇸"),
        Language(3, "es", "Español 🇪🇸"),
        Language(4, "fr", "Français 🇫🇷"),
        Language(5, "de", "Deutsch 🇩🇪"),
        Language(6, "ar", "العربية 🇸🇦"),
        Language(7, "zh", "中文 🇨🇳"),
        Language(8, "hi", "हिन्दी 🇮🇳")
    )
    /*
    val languageNames = mapOf(
        "tr" to "Türkçe 🇹🇷",
        "en" to "English 🇺🇸",
        "es" to "Español 🇪🇸",
        "fr" to "Français 🇫🇷",
        "de" to "Deutsch 🇩🇪",
        "ar" to "العربية 🇸🇦",
        "zh" to "中文 🇨🇳",
        "hi" to "हिन्दी 🇮🇳"
    )*/

    Box(modifier = Modifier.padding(16.dp)) {
        TextButton(onClick = { expanded = true }) {
            Text(languageList.first { it.id == currentLangId }.name)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            languages.keys.forEach { langId ->
                DropdownMenuItem(
                    text = {
                        Text(text = languageList.first { it.id == langId }.name)
                    },
                    onClick = {
                        onLangSelected(langId)
                        expanded = false
                    }
                )
            }
        }
    }
}