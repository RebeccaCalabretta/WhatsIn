package de.syntax_institut.androidabschlussprojekt.ui.components.scan

import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ErrorDialog

@Composable
fun ScanScreenContent(
    hasCameraPermission: Boolean,
    previewView: PreviewView,
    productError: ProductError?,
    onErrorDismiss: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (hasCameraPermission) {
            ScanPreview(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clipToBounds(),
                previewView = previewView
            )
        } else {
            Text(stringResource(R.string.camera_permission))
        }

        ScanPreviewSubtitle()

        Spacer(Modifier.height(20.dp))

        if (productError != null) {
            ErrorDialog(
                message = stringResource(productError.messageRes),
                onDismiss = onErrorDismiss
            )
        }
    }
}