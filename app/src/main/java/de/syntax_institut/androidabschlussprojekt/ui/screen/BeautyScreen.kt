package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanHistory
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BeautyScreen(
    productViewModel: ProductViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val scannedProducts by productViewModel.scannedProducts.collectAsState()
    val beautyProducts = scannedProducts.filter { it.productType == "beauty" }

    ScanHistory(
        scannedProducts = beautyProducts,
        onNavigateToDetail = onNavigateToDetail,
        title = null
    )
}