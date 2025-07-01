package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.model.toScannedProduct
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.SearchField
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanHistory
import de.syntax_institut.androidabschlussprojekt.viewmodel.CollectionViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BeautyScreen(
    collectionViewModel: CollectionViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val beautyProducts by collectionViewModel.filteredBeautyProducts.collectAsState()
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

        ScanHistory(
            scannedProducts = beautyProducts.map { it.toScannedProduct() },
            onNavigateToDetail = onNavigateToDetail,
            title = null
        )
    }
}