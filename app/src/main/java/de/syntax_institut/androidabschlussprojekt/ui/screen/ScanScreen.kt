package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.PermissionSettingsDialog
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanScreenContent
import de.syntax_institut.androidabschlussprojekt.utils.scan.checkCameraPermission
import de.syntax_institut.androidabschlussprojekt.utils.scan.hasInternet
import de.syntax_institut.androidabschlussprojekt.utils.scan.observeCameraLifecycle
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ScanViewModel
import kotlinx.coroutines.delay
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

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (isGranted) {
            scanViewModel.setupCamera(context, lifecycleOwner, previewView)
        }
    }

    var hasRequestedCameraPermission by rememberSaveable { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!hasInternet(context)) {
            snackbarHostState.showSnackbar(context.getString(R.string.error_network))
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                checkCameraPermission(
                    context = context,
                    hasRequestedBefore = hasRequestedCameraPermission,
                    requestLauncher = requestCameraPermissionLauncher,
                    onShowSettingsDialog = {
                        showSettingsDialog = true
                    },
                    onPermissionGranted = {
                        hasCameraPermission = true
                        scanViewModel.setupCamera(context, lifecycleOwner, previewView)
                    }
                )
                hasRequestedCameraPermission = true
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
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
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
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

    if (showSettingsDialog) {
        PermissionSettingsDialog(
            context = context,
            onDismiss = { showSettingsDialog = false }
        )
    }
}