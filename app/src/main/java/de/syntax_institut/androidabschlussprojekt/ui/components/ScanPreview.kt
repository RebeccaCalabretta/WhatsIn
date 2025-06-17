package de.syntax_institut.androidabschlussprojekt.ui.components

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

@Composable
fun ScanPreview(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    AndroidView(
        factory = { previewView },
        modifier = modifier
    ) {
        Log.d("ScanPreview", "Composable gestartet")

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            Log.d("ScanPreview", "CameraProvider verfügbar")

            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                Log.d("ScanPreview", "Preview erstellt und SurfaceProvider gesetzt")
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            Log.d("ScanPreview", "Kamera-Selektor: Rückkamera")

            try {
                cameraProvider.unbindAll()
                Log.d("ScanPreview", "Alte Kamera-Verbindungen entfernt")

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview
                )
                Log.d("ScanPreview", "Kamera an Lifecycle gebunden – Vorschau läuft")
            } catch (e: Exception) {
                Log.e("ScanPreview", "Fehler beim Kamera-Setup", e)
            }

        }, ContextCompat.getMainExecutor(context))
    }
}