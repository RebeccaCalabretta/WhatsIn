package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun FilterViolationDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.product_not_matching_filter)) }
    )
}