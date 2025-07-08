package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.ProductCollection
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.SortDropdown
import de.syntax_institut.androidabschlussprojekt.viewmodel.CollectionViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FoodScreen(
    snackbarHostState: SnackbarHostState,
    collectionViewModel: CollectionViewModel = koinViewModel(),
    productViewModel: ProductViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val products by collectionViewModel
        .getProductsByType(ProductType.FOOD)
        .collectAsState()

    val searchText by collectionViewModel.searchText.collectAsState()
    val sortOption by collectionViewModel.sortOption.collectAsState()
    val violationsMap by collectionViewModel.filterViolationsMap.collectAsState()

    var showSearch by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Suche",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showSearch = !showSearch }
            )

            if (showSearch) {
                Spacer(modifier = Modifier.width(4.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    BasicTextField(
                        value = searchText,
                        onValueChange = { collectionViewModel.updateSearchText(it) },
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                        modifier = Modifier.fillMaxWidth(),
                        decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.weight(1f)) {
                                    if (searchText.isBlank()) {
                                        Text(
                                            text = "Suche...",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    innerTextField()
                                }
                                if (searchText.isNotBlank()) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Text löschen",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .padding(start = 4.dp)
                                            .clickable { collectionViewModel.updateSearchText("") }
                                    )
                                }
                            }
                        }
                    )
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            SortDropdown(
                currentOption = sortOption,
                onOptionSelected = { collectionViewModel.updateSortOption(it) },
                labelOnly = showSearch,
                onCollapseSearch = { showSearch = false }
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
                onNavigateToDetail = onNavigateToDetail,
                onDeleteProduct = { barcode -> productViewModel.deleteProduct(barcode) }
            )
        }
    }
}