package com.memoit.ddimovski.memoit.android.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.memoit.ddimovski.memoit.domain.note.Note

@Composable
fun NoteListItem(
    note: Note,
    isLongPressed: Boolean,
    onCompleteChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var checked by mutableStateOf(note.isCompleted)
    Card(
        modifier = modifier.height(200.dp).fillMaxWidth(),
        border = if (isLongPressed) BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceTint) else null
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(0.6f)
            )

            ActionIcon(
                Icons.Default.Check,
                "Complete task",
                onClick = {
                    checked = !checked
                    onCompleteChanged(checked)
                },
                background = if (!checked) MaterialTheme.colorScheme.surface.copy(alpha = 0.4f) else MaterialTheme.colorScheme.onSurfaceVariant,
                tint = if (!checked) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface,
            )
        }
        Text(
            text = note.description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f).padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            overflow = TextOverflow.Ellipsis
        )


    }
}
