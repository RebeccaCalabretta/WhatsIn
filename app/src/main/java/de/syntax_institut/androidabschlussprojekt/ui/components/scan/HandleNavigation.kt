package de.syntax_institut.androidabschlussprojekt.ui.components.scan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.delay

@Composable
fun HandleNavigation(
    scannedBarcode: String?,
    product: Product?,
    productError: ProductError?,
    onNavigateToDetail: (String) -> Unit,
    onProductScanned: (String) -> Unit,
    onResetScan: () -> Unit,
    stopCamera: () -> Unit
) {
    LaunchedEffect(scannedBarcode) {
        scannedBarcode?.let {
            onProductScanned(it)
            onResetScan()
        }
    }

    LaunchedEffect(product, productError) {
        if (product != null && productError == null) {
            stopCamera()
            delay(300)
            onNavigateToDetail(product.barcode)
        }
    }

    LaunchedEffect(productError) {
        if (productError != null) {
            stopCamera()
        }
    }
}