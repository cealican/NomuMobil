package alican.app.nomu.ui.Component

import alican.app.nomu.util.NomuColor.Companion.NomuBackground
import alican.app.nomu.util.NomuColor.Companion.NomuPurple
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties

@Composable
fun AutocompleteField(
    label: String,
    placeholder: String? = null,
    value: String,
    suggestions: List<String>,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit,
    onSuggestionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    expanded = it.length >= 2 && suggestions.isNotEmpty()
                                },
                label = { Text(label) },
                placeholder = {
                    if (placeholder != null) {
                        Text(placeholder)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = NomuPurple) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = NomuBackground,
                    unfocusedContainerColor = NomuBackground,
                    disabledContainerColor = NomuBackground,
                    focusedBorderColor = NomuPurple,
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedLabelColor = NomuPurple,
                    unfocusedLabelColor = Color.Gray
                ),
                singleLine = true
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.85f).background(NomuBackground),
                properties = PopupProperties(focusable = false) // Klavye kapanmasın diye
            ) {
                suggestions.forEach { suggestion ->
                    DropdownMenuItem(
                        text = { Text(suggestion) },
                        onClick = {
                            onSuggestionSelected(suggestion)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}