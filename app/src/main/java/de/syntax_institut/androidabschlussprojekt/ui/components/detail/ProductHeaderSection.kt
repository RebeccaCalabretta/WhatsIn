package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    filterViolations: List<String>,
    labels: List<String>
) {
    var showImageDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
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


        ProductLabelSection(labels)
    }

    ProductImagePreview(
        visible = showImageDialog && !imageUrl.isNullOrBlank(),
        imageUrl = imageUrl,
        onDismiss = { showImageDialog = false }
    )
}