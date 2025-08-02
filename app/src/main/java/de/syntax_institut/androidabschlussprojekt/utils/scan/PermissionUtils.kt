package de.syntax_institut.androidabschlussprojekt.utils.scan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    context.startActivity(intent)
}

fun checkCameraPermission(
    context: Context,
    hasRequestedBefore: Boolean,
    requestLauncher: ActivityResultLauncher<String>,
    onShowSettingsDialog: () -> Unit,
    onPermissionGranted: () -> Unit
) {
    val permission = android.Manifest.permission.CAMERA
    val activity = context as? Activity

    when {
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
            onPermissionGranted()
        }

        !hasRequestedBefore -> {
            requestLauncher.launch(permission)
        }

        activity != null && ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
            requestLauncher.launch(permission)
        }

        else -> {
            onShowSettingsDialog()
        }
    }
}