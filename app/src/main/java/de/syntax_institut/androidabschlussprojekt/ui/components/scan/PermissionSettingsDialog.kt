package de.syntax_institut.androidabschlussprojekt.ui.components.scan

import android.content.Context
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.utils.openAppSettings

@Composable
fun PermissionSettingsDialog(
    context: Context,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.permission_dialog_title)) },
        text = { Text(text = stringResource(R.string.permission_dialog_text)) },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                openAppSettings(context)
            }) {
                Text(stringResource(R.string.permission_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.permission_dialog_cancel))
            }
        }
    )
}