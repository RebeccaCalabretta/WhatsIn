package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanScreenContent
import de.syntax_institut.androidabschlussprojekt.utils.observeCameraLifecycle
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ScanViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScanScreen(
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

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    LaunchedEffect(scannedBarcode) {
        scannedBarcode?.let {
            productViewModel.startScan(it)
            scanViewModel.resetScan()
        }
    }

    LaunchedEffect(product, productError) {
        if (product != null && productError == null) {
            scanViewModel.stopCamera(previewView)
            delay(300)
            onNavigateToDetail(product!!.barcode)
            productViewModel.clearSelectedProduct()
        }
    }

    LaunchedEffect(productError) {
        if (productError != null) {
            scanViewModel.stopCamera(previewView)
        }
    }

    DisposableEffect(hasCameraPermission) {
        val observer = observeCameraLifecycle(
            context = context,
            lifecycleOwner = lifecycleOwner,
            previewView = previewView,
            scanViewModel = scanViewModel,
            hasPermission = hasCameraPermission
        )

        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }

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
}