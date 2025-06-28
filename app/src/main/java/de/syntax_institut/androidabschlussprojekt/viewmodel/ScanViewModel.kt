package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScanViewModel : ViewModel() {

    private val _scannedBarcode = MutableStateFlow<String?>(null)
    val scannedBarcode: StateFlow<String?> = _scannedBarcode

    private var lastScanned: String? = null
    private var isCameraRunning = false

    fun onBarcodeScanned(barcode: String) {
        if (barcode != lastScanned) {
            lastScanned = barcode
            _scannedBarcode.value = barcode
            Log.d("ScanViewModel", "Barcode erkannt: $barcode")
        }
    }

    fun resetScan() {
        _scannedBarcode.value = null
        lastScanned = null
    }

    @OptIn(ExperimentalGetImage::class)
    fun setupCamera(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView
    ) {
        if (isCameraRunning) return
        isCameraRunning = true

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val barcodeScanner = BarcodeScanning.getClient()

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                val mediaImage = imageProxy.image
                if (mediaImage != null) {
                    val inputImage =
                        InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

                    barcodeScanner.process(inputImage)
                        .addOnSuccessListener { barcodes ->
                            barcodes.firstOrNull()?.rawValue?.let { onBarcodeScanned(it) }
                        }
                        .addOnFailureListener { Log.e("ScanViewModel", "Scanfehler", it) }
                        .addOnCompleteListener { imageProxy.close() }
                } else {
                    imageProxy.close()
                }
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )
                Log.d("ScanViewModel", "Kamera gebunden")
            } catch (e: Exception) {
                Log.e("ScanViewModel", "Fehler beim Binden", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun stopCamera(previewView: PreviewView) {
        try {
            val cameraProvider = ProcessCameraProvider.getInstance(previewView.context).get()
            cameraProvider.unbindAll()
            isCameraRunning = false
            Log.d("ScanViewModel", "Kamera gestoppt")
        } catch (e: Exception) {
            Log.e("ScanViewModel", "Fehler beim Stoppen", e)
        }
    }
}