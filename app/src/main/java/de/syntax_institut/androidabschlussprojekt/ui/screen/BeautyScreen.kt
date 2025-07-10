package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.CollectionHeader
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.ProductCollection
import de.syntax_institut.androidabschlussprojekt.viewmodel.CollectionViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun BeautyScreen(
    snackbarHostState: SnackbarHostState,
    collectionViewModel: CollectionViewModel = koinViewModel(),
    productViewModel: ProductViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val products by collectionViewModel
        .getProductsByType(ProductType.BEAUTY)
        .collectAsState()

    val searchText by collectionViewModel.searchText.collectAsState()
    val sortOption by collectionViewModel.sortOption.collectAsState()
    val violationsMap by collectionViewModel.filterViolationsMap.collectAsState()
    var showSearch by remember { mutableStateOf(false) }

    val productRemoved = stringResource(R.string.product_removed)
    val undo = stringResource(R.string.undo)
    val noProductsSaved = stringResource(R.string.no_products_saved)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        CollectionHeader(
            searchText = searchText,
            onSearchTextChange = { collectionViewModel.updateSearchText(it) },
            sortOption = sortOption,
            onSortOptionSelected = { collectionViewModel.updateSortOption(it) },
            showSearch = showSearch,
            onToggleSearch = { showSearch = !showSearch },
            onCollapseSearch = { showSearch = false }
        )

        if (products.isEmpty()) {
            Text(
                text = noProductsSaved,
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

    LaunchedEffect(Unit) {
        productViewModel.snackbarMessage.collectLatest { key ->
            val message = when (key) {
                "product_removed" -> productRemoved
                else -> key
            }
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = if (key == "product_removed") undo else null
            )
            if (result == SnackbarResult.ActionPerformed && key == "product_removed") {
                productViewModel.undoDelete()
            }
        }
    }
}