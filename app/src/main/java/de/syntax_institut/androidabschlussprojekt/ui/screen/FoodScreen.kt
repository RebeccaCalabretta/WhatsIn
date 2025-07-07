package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.ProductCollection
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.SearchField
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.SortDropdown
import de.syntax_institut.androidabschlussprojekt.viewmodel.CollectionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FoodScreen(
    collectionViewModel: CollectionViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val products by collectionViewModel
        .getProductsByType(ProductType.FOOD)
        .collectAsState()

    val searchText by collectionViewModel.searchText.collectAsState()
    val violationsMap by collectionViewModel.filterViolationsMap.collectAsState()
    val sortOption by collectionViewModel.sortOption.collectAsState()

    var showSearch by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { showSearch = !showSearch }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Suche"
                )
            }

            SortDropdown(
                currentOption = sortOption,
                onOptionSelected = { collectionViewModel.updateSortOption(it) },
            )
        }

        if (showSearch) {
            SearchField(
                text = searchText,
                onTextChange = { collectionViewModel.updateSearchText(it) }
            )
        }

        if (products.isEmpty()) {
            Text(
                text = "Keine Produkte gefunden",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            ProductCollection(
                products = products,
                violationsMap = violationsMap,
                onNavigateToDetail = onNavigateToDetail
            )
        }
    }
}