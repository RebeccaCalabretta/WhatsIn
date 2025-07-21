package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.camera.view.PreviewView
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.CameraPermissionHandler
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.HandleNavigation
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.PermissionSettingsDialog
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanScreenContent
import de.syntax_institut.androidabschlussprojekt.utils.scan.hasInternet
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ScanViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScanScreen(
    snackbarHostState: SnackbarHostState,
    productViewModel: ProductViewModel = koinViewModel(),
    scanViewModel: ScanViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    val scannedBarcode by scanViewModel.scannedBarcode.collectAsState()
    val product by productViewModel.selectedProduct.collectAsState()
    val productError by productViewModel.productError.collectAsState()

    var showSettingsDialog by remember { mutableStateOf(false) }
    var hasRequestedCameraPermission by rememberSaveable { mutableStateOf(false) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    LaunchedEffect(Unit) {
        if (!hasInternet(context)) {
            snackbarHostState.showSnackbar(context.getString(R.string.error_network))
        }
    }

    CameraPermissionHandler(
        context = context,
        lifecycleOwner = lifecycleOwner,
        previewView = previewView,
        scanViewModel = scanViewModel,
        hasCameraPermission = hasCameraPermission,
        hasRequestedCameraPermission = hasRequestedCameraPermission,
        onPermissionGranted = { hasCameraPermission = true },
        onRequestPermission = { hasRequestedCameraPermission = true },
        onShowSettingsDialog = { showSettingsDialog = true }
    )

    HandleNavigation(
        scannedBarcode = scannedBarcode,
        product = product,
        productError = productError,
        onNavigateToDetail = { barcode ->
            onNavigateToDetail(barcode)
            productViewModel.clearSelectedProduct()
        },
        onProductScanned = { barcode ->
            productViewModel.startScan(barcode)
        },
        onResetScan = {
            scanViewModel.resetScan()
        },
        stopCamera = { scanViewModel.stopCamera(previewView) }
    )

    ScanScreenContent(
        hasCameraPermission = hasCameraPermission,
        previewView = previewView,
        productError = productError,
        onErrorDismiss = {
            productViewModel.clearProductError()
            scanViewModel.resetScan()
            scanViewModel.setupCamera(context, lifecycleOwner, previewView)
        }
    )

    if (showSettingsDialog) {
        PermissionSettingsDialog(
            context = context,
            onDismiss = { showSettingsDialog = false }
        )
    }
}