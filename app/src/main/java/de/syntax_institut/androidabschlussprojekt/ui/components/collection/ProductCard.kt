package de.syntax_institut.androidabschlussprojekt.ui.components.collection

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.data.dummyProduct
import de.syntax_institut.androidabschlussprojekt.model.Product
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ProductImage
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme

@Composable
fun ProductCard(
    product: Product,
    isFilterMatch: Boolean,
    isFavorite: Boolean,
    timestamp: Long,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProductImage(product.imageUrl)
            Spacer(modifier = Modifier.width(12.dp))
            ProductCardContent(
                product = product,
                isFilterMatch = isFilterMatch,
                isFavorite = isFavorite,
                timestamp = timestamp
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ProductCardPreview() {
    AndroidAbschlussprojektTheme {
        ProductCard(
            product = dummyProduct,
            isFilterMatch = false,
            isFavorite = true,
            timestamp = System.currentTimeMillis() - 5 * 60 * 1000L,
            onClick = {}
        )
    }
}