package de.syntax_institut.androidabschlussprojekt.ui.components.general

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    message: String,
    title: String = "Fehler",
    onDismiss: () -> Unit
) {
   AlertDialog(
       onDismissRequest = onDismiss,
       title = { Text(title) },
       text = { Text(message) },
       confirmButton = {
           TextButton(onClick = onDismiss) {
               Text("OK")
           }
       }
   )
}