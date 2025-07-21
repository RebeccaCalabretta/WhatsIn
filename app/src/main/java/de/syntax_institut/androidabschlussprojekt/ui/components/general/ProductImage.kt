package de.syntax_institut.androidabschlussprojekt.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun ProductImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(R.string.product_image),
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(
                if (onClick != null && !imageUrl.isNullOrBlank()) Modifier.clickable { onClick() }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (!imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.ImageNotSupported,
                contentDescription = stringResource(R.string.no_image),
                tint = MaterialTheme.colorScheme.outline,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}
