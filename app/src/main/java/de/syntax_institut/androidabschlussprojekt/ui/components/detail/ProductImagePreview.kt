package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close

@Composable
fun ProductImagePreview(
    visible: Boolean,
    imageUrl: String?,
    onDismiss: () -> Unit
) {
    if (visible && !imageUrl.isNullOrBlank()) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Produktbild vergrößert",
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(1f)
                        .clickable { onDismiss() }
                )
                IconButton(
                    onClick = { onDismiss() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(24.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Schließen", tint = Color.White)
                }
            }
        }
    }
}