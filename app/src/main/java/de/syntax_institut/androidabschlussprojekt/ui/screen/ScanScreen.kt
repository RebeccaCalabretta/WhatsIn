package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.dummyProduct
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ErrorDialog
import de.syntax_institut.androidabschlussprojekt.ui.components.general.GeneralButton
import de.syntax_institut.androidabschlussprojekt.ui.components.scan.ScanPreview
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

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    if (hasCameraPermission) {
                        scanViewModel.setupCamera(context, lifecycleOwner, previewView)
                    }
                }

                Lifecycle.Event.ON_PAUSE -> {
                    scanViewModel.stopCamera(previewView)
                }

                else -> {}
            }
        }

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
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clipToBounds()
            ) {
                val scanFraction = 0.7f
                val stroke = 2.dp
                val scanSize = maxWidth * scanFraction
                val overlaySize = scanSize + stroke * 2
                val radius = 8.dp

                ScanPreview(
                    modifier = Modifier.fillMaxSize(),
                    previewView = previewView
                )

                Canvas(modifier = Modifier.matchParentSize()) {
                    val overlayColor = Color(0xAA000000)
                    drawRect(overlayColor)

                    val sizePx = size.width * scanFraction
                    val topLeft = Offset(
                        (size.width - sizePx) / 2f,
                        (size.height - sizePx) / 2f
                    )

                    drawRoundRect(
                        color = Color.Transparent,
                        topLeft = topLeft,
                        size = Size(sizePx, sizePx),
                        cornerRadius = CornerRadius(radius.toPx()),
                        blendMode = BlendMode.Clear
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.crop_frame),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(overlaySize)
                )
            }
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
                onDismiss = {
                    productViewModel.clearProductError()
                    scanViewModel.resetScan()
                    scanViewModel.setupCamera(context, lifecycleOwner, previewView)
                }
            )
        }
    }
}