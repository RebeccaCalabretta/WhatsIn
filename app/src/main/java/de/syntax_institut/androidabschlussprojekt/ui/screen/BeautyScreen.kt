package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.syntax_institut.androidabschlussprojekt.model.toScannedProduct
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.SearchField
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanHistory
import de.syntax_institut.androidabschlussprojekt.viewmodel.CollectionViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BeautyScreen(
    productViewModel: ProductViewModel = koinViewModel(),
    collectionViewModel: CollectionViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val beautyProducts by productViewModel.beautyProducts.collectAsState()

    val searchText by collectionViewModel.searchText.collectAsState()

    SearchField(
        text =searchText,
        onTextChange = { collectionViewModel.updateSearchText(it)}
    )

    ScanHistory(
        scannedProducts = beautyProducts.map {  it.toScannedProduct() },
        onNavigateToDetail = onNavigateToDetail,
        title = null
    )
}