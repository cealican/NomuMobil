package alican.app.nomu.ui.Component

import alican.app.nomu.util.NomuColor
import alican.app.nomu.util.NomuColor.Companion.NomuBackground
import alican.app.nomu.util.NomuColor.Companion.NomuPurple
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
@Composable
fun NomuTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = NomuPurple) },
            trailingIcon = trailingIcon,
            readOnly = readOnly,
            // MATERIAL 3 GÜNCEL RENK APISI
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
    }
}