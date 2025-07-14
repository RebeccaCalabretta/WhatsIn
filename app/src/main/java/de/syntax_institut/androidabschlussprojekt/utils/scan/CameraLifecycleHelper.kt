package de.syntax_institut.androidabschlussprojekt.utils.scan

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import de.syntax_institut.androidabschlussprojekt.viewmodel.ScanViewModel

fun observeCameraLifecycle(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    previewView: PreviewView,
    scanViewModel: ScanViewModel,
    hasPermission: Boolean
): LifecycleEventObserver {
    return LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                if (hasPermission) {
                    scanViewModel.setupCamera(context, lifecycleOwner, previewView)
                }
            }

            Lifecycle.Event.ON_PAUSE -> {
                scanViewModel.stopCamera(previewView)
            }

            else -> {}
        }
    }
}