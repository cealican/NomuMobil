package alican.app.nomu.ui.Component

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
fun LanguagePicker(currentLang: String, onLangSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val languageNames = mapOf(
        "tr" to "Türkçe 🇹🇷",
        "en" to "English 🇺🇸",
        "es" to "Español 🇪🇸",
        "fr" to "Français 🇫🇷",
        "de" to "Deutsch 🇩🇪",
        "ar" to "العربية 🇸🇦",
        "zh" to "中文 🇨🇳",
        "hi" to "हिन्दी 🇮🇳"
    )

    Box(modifier = Modifier.padding(16.dp)) {
        TextButton(onClick = { expanded = true }) {
            Text(languageNames[currentLang] ?: "🌐 Language")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            languages.keys.forEach { langCode ->
                DropdownMenuItem(
                    text = {
                        Text(text = languageNames[langCode] ?: langCode.uppercase())
                    },
                    onClick = {
                        onLangSelected(langCode)
                        expanded = false
                    }
                )
            }
        }
    }
}