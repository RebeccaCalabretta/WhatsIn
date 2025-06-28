package de.syntax_institut.androidabschlussprojekt.ui.components.scan

import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ScanPreview(
    modifier: Modifier = Modifier,
    previewView: PreviewView
) {
    AndroidView(
        factory = { previewView },
        modifier = modifier
    )
}