package de.syntax_institut.androidabschlussprojekt.ui.components.scan

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProduct
import de.syntax_institut.androidabschlussprojekt.data.local.model.toProduct
import de.syntax_institut.androidabschlussprojekt.ui.components.ProductCard


@Composable
fun ScanHistory(
    scannedProducts: List<ScannedProduct>,
    onNavigateToDetail: (String) -> Unit,
    title: String? = "Verlauf"
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (!title.isNullOrBlank()) {
            item {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        items(scannedProducts) { scannedProduct ->
            ProductCard(
                product = scannedProduct.toProduct(),
                isFilterMatch = true,
                onClick = { onNavigateToDetail(scannedProduct.barcode) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}