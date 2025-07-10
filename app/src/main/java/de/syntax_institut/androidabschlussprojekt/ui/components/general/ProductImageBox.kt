package de.syntax_institut.androidabschlussprojekt.ui.components.general

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.R

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun ProductImageBox(
    imageUrl: String?,
    imageSizeRatio: Float,
    onImageClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val imageSize = screenWidth * imageSizeRatio

    Box(
        modifier = Modifier
            .size(imageSize)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.LightGray)
            .clickable(enabled = imageUrl != null) {
                if (!imageUrl.isNullOrBlank()) onImageClick()
            },
        contentAlignment = Alignment.Center
    ) {
        if (!imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(R.string.product_image),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(stringResource(R.string.no_image), style = MaterialTheme.typography.bodySmall)
        }
    }
}