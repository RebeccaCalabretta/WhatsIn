package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.helper.SortOption
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.ProductCollection
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.SearchField
import de.syntax_institut.androidabschlussprojekt.utils.toUiLabel
import de.syntax_institut.androidabschlussprojekt.viewmodel.CollectionViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchField(
            text = searchText,
            onTextChange = { collectionViewModel.updateSearchText(it) }
        )

        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp )
        ) {
            TextField(
                value = sortOption.toUiLabel(),
                onValueChange = {},
                readOnly = true,
                label = { Text("Sortieren nach") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                SortOption.entries.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.toUiLabel()) },
                        onClick = {
                            collectionViewModel.updateSortOption(option)
                            expanded = false
                        }
                    )
                }
            }
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