package com.memoit.ddimovski.memoit.android.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle,
    keyboardOptions: KeyboardOptions,
    hint: String,
    modifier: Modifier = Modifier,
    error: String? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        keyboardOptions = keyboardOptions,
        isError = error != null,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        label = {
            Text(
                hint,
                style = MaterialTheme.typography.labelSmall.copy(fontStyle = FontStyle.Italic)
            )
        },
        supportingText = {
            Text(
                error ?: "",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            )
        }
    )
}
