package de.syntax_institut.androidabschlussprojekt.ui.components.collection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import de.syntax_institut.androidabschlussprojekt.model.Product
import de.syntax_institut.androidabschlussprojekt.ui.components.ProductCard

@Composable
fun ProductCollection(
    products: List<Product>,
    violationsMap: Map<String, List<String>>,
    onNavigateToDetail: (String) -> Unit,
    title: String? = null
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (!title.isNullOrBlank()) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            items(products) { product ->
                val isMatch = violationsMap[product.barcode].isNullOrEmpty()

                ProductCard(
                    product = product,
                    isFilterMatch = isMatch,
                    isFavorite = product.isFavorite,
                    timestamp = product.timestamp,
                    onClick = { onNavigateToDetail(product.barcode) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}