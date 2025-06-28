package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import de.syntax_institut.androidabschlussprojekt.data.dummyProduct
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ErrorDialog
import de.syntax_institut.androidabschlussprojekt.ui.components.general.GeneralButton
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanHistory
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanPreview
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ScanViewModel
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

    val scannedProducts by productViewModel.scannedProducts.collectAsState()
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
            onNavigateToDetail(product!!.barcode)
        }
    }

    if (hasCameraPermission) {
        LaunchedEffect(previewView) {
            scanViewModel.setupCamera(context, lifecycleOwner, previewView)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        if (hasCameraPermission) {
            ScanPreview(
                modifier = Modifier
                    .size(300.dp)
                    .clipToBounds(),
                previewView = previewView
            )
        } else {
            Text("Kamera-Berechtigung erforderlich")
        }

        Text(
            text = "Halte den Barcode vor die Kamera",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

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
                onDismiss = { productViewModel.clearProductError() }
            )
        }

        ScanHistory(
            scannedProducts = scannedProducts,
            onNavigateToDetail = onNavigateToDetail
        )
    }
}