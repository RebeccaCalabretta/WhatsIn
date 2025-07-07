package de.syntax_institut.androidabschlussprojekt.utils.scan

import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.model.Product
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ErrorDialog
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ScanViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun handleScannedBarcode(
    barcode: String?,
    scanViewModel: ScanViewModel,
    productViewModel: ProductViewModel
) {
    barcode?.let {
        productViewModel.startScan(it)
        scanViewModel.resetScan()
    }
}

fun handleScanSuccess(
    product: Product?,
    productError: ProductError?,
    previewView: PreviewView,
    productViewModel: ProductViewModel,
    scanViewModel: ScanViewModel,
    scope: CoroutineScope,
    onNavigateToDetail: (String) -> Unit
) {
    if (product != null && productError == null) {
        scanViewModel.stopCamera(previewView)
        scope.launch {
            delay(300)
            onNavigateToDetail(product.barcode)
            productViewModel.clearSelectedProduct()
        }
    }
}

fun handleProductError(
    error: ProductError?,
    onDismiss: () -> Unit
): (@Composable () -> Unit)? {
    return error?.let {
        {
            ErrorDialog(
                message = it.message,
                onDismiss = onDismiss
            )
        }
    }
}