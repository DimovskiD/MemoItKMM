package com.memoit.ddimovski.memoit.android.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MainFAB(imageVector: ImageVector, contentDescription: String, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}
