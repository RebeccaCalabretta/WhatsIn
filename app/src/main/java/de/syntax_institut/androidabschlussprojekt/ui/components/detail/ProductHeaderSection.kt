package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ProductImageBox

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ProductHeaderSection(
    imageUrl: String?,
    name: String?,
    brand: String?,
    corporation: String?,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    filterViolations: List<String>
) {
    var showImageDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.Top
        ) {
            ProductImageBox(
                imageUrl = imageUrl,
                imageSizeRatio = 0.20f,
                onImageClick = { showImageDialog = true }
            )

            Spacer(modifier = Modifier.width(16.dp))

            ProductInfoSection(
                name = name,
                brand = brand,
                corporation = corporation,
                isFavorite = isFavorite,
                onToggleFavorite = onToggleFavorite
            )
        }

        CriteriaStatusSection(filterViolations)
    }

    if (showImageDialog && !imageUrl.isNullOrBlank()) {
        Dialog(onDismissRequest = { showImageDialog = false }) {
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
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable { showImageDialog = false }
                )
                IconButton(
                    onClick = { showImageDialog = false },
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