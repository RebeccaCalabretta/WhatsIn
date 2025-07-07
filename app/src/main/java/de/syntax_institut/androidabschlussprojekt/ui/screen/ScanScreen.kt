package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import de.syntax_institut.androidabschlussprojekt.data.dummyProduct
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ErrorDialog
import de.syntax_institut.androidabschlussprojekt.ui.components.general.GeneralButton
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanPreview
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanPreviewSubtitle
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

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasCameraPermission) {
            ScanPreview(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clipToBounds(),
                previewView = previewView
            )
        } else {
            Text("Kamera-Berechtigung erforderlich")
        }

        ScanPreviewSubtitle()

        Spacer(Modifier.height(20.dp))

        GeneralButton(
            text = "Scannen",
            onClick = {
                onNavigateToDetail(dummyProduct.barcode)
            }
        )

        productError?.let {
            ErrorDialog(
                message = it.message,
                onDismiss = {
                    productViewModel.clearProductError()
                    scanViewModel.resetScan()
                    scanViewModel.setupCamera(context, lifecycleOwner, previewView)
                }
            )
        }
    }
}