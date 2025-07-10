package de.syntax_institut.androidabschlussprojekt.ui.components.general

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun ErrorDialog(
    message: String,
    title: String = stringResource(R.string.error),
    onDismiss: () -> Unit
) {
   AlertDialog(
       onDismissRequest = onDismiss,
       title = { Text(title) },
       text = { Text(message) },
       confirmButton = {
           TextButton(onClick = onDismiss) {
               Text(stringResource(R.string.ok))
           }
       }
   )
}