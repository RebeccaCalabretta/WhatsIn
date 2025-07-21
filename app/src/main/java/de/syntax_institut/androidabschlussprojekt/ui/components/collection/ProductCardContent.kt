package de.syntax_institut.androidabschlussprojekt.ui.components.collection

import android.text.format.DateUtils
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.model.Product

@Composable
fun ProductCardContent(
    product: Product,
    isFilterMatch: Boolean,
    isFavorite: Boolean,
    timestamp: Long
) {
    Column(
        modifier = Modifier.padding(end = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = product.name ?: stringResource(R.string.unknown),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 28.dp)
            )

            if (isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favorite),
                    tint = Color.Red,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (isFilterMatch) Icons.Default.CheckCircle else Icons.Default.Warning,
                contentDescription = null,
                tint = if (isFilterMatch)
                    Color(0xFF60BD65)
                else
                    Color(0xFFFFC107)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = DateUtils.getRelativeTimeSpanString(
                    timestamp,
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS
                ).toString(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}