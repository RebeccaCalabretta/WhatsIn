package de.syntax_institut.androidabschlussprojekt.ui.components.scan

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.utils.scan.openAppSettings

@Composable
fun PermissionSettingsDialog(
    context: Context,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.permission_dialog_title)) },
        text = { Text(stringResource(R.string.permission_dialog_text)) },
        confirmButton = {
            Row {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.permission_dialog_cancel),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                TextButton(
                    onClick = {
                        onDismiss()
                        openAppSettings(context)
                    },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(stringResource(R.string.permission_dialog_confirm))
                }
            }
        }
    )
}