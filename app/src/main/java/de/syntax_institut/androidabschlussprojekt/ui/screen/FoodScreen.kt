package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.model.toScannedProduct
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.SearchField
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanHistory
import de.syntax_institut.androidabschlussprojekt.viewmodel.CollectionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FoodScreen(
    collectionViewModel: CollectionViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val searchedProducts by collectionViewModel.filteredFoodProducts.collectAsState()
    val searchText by collectionViewModel.searchText.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchField(
            text = searchText,
            onTextChange = { collectionViewModel.updateSearchText(it) }
        )
        if (searchedProducts.isEmpty()) {
            Text(
                text = "Keine Produkte gefunden",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            ScanHistory(
                scannedProducts = searchedProducts.map { it.toScannedProduct() },
                onNavigateToDetail = onNavigateToDetail,
                title = null
            )
        }
    }
}