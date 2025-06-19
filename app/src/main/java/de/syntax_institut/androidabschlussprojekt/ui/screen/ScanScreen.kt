package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.content.pm.PackageManager
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import de.syntax_institut.androidabschlussprojekt.data.dummyProduct
import de.syntax_institut.androidabschlussprojekt.ui.components.Scan.ScanPreview
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ErrorDialog
import de.syntax_institut.androidabschlussprojekt.viewModel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScanScreen(
    productViewModel: ProductViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current

    // ✅ Kamera-Permission prüfen & verwalten
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

    // ✅ Berechtigung einmal beim Start anfordern
    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val product by productViewModel.selectedProduct.collectAsState()
    val productError by productViewModel.productError.collectAsState()

    // ✅ Navigation nur bei gültigem Produkt & ohne Fehler
    LaunchedEffect(product, productError) {
        val currentProduct = product
        if (currentProduct != null && productError == null) {
            onNavigateToDetail(currentProduct.barcode)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Scanner Vorschau")
        Spacer(Modifier.height(40.dp))

        // ✅ Scanner nur zeigen, wenn Permission erteilt wurde
        if (hasCameraPermission) {
            ScanPreview(
                productViewModel = productViewModel,
                modifier = Modifier
                    .size(300.dp)
                    .clipToBounds()
            )
        } else {
            Text("Kamera-Berechtigung erforderlich")
        }

        Spacer(Modifier.height(40.dp))

        Button(onClick = {
            onNavigateToDetail(dummyProduct.barcode)
        }) {
            Text("Scannen")
        }
    }

    // ✅ Fehlerdialog bei Scanfehler
    if (productError != null) {
        ErrorDialog(
            message = productError!!.message,
            onDismiss = { productViewModel.clearProductError() }
        )
    }
}