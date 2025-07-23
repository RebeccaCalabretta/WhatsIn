package de.syntax_institut.androidabschlussprojekt.ui.components.scan

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import de.syntax_institut.androidabschlussprojekt.utils.scan.checkCameraPermission
import de.syntax_institut.androidabschlussprojekt.utils.scan.observeCameraLifecycle
import de.syntax_institut.androidabschlussprojekt.viewmodel.ScanViewModel

@Composable
fun CameraPermissionHandler(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    scanViewModel: ScanViewModel,
    requestLauncher: ActivityResultLauncher<String>,
    hasCameraPermission: Boolean,
    hasRequestedCameraPermission: Boolean,
    onPermissionGranted: () -> Unit,
    onShowSettingsDialog: () -> Unit,
    onRequestPermission: () -> Unit
) {
    DisposableEffect(lifecycleOwner, hasRequestedCameraPermission) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                checkCameraPermission(
                    context = context,
                    hasRequestedBefore = hasRequestedCameraPermission,
                    requestLauncher = requestLauncher,
                    onPermissionGranted = {
                        onPermissionGranted()
                        scanViewModel.setupCamera(context, lifecycleOwner, previewView)
                    },
                    onShowSettingsDialog = onShowSettingsDialog
                )
                onRequestPermission()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    DisposableEffect(hasCameraPermission) {
        val cameraObserver = observeCameraLifecycle(
            context = context,
            lifecycleOwner = lifecycleOwner,
            previewView = previewView,
            scanViewModel = scanViewModel,
            hasPermission = hasCameraPermission
        )

        lifecycleOwner.lifecycle.addObserver(cameraObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(cameraObserver)
        }
    }
}