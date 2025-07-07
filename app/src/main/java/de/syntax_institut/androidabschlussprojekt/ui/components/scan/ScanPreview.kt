package de.syntax_institut.androidabschlussprojekt.ui.components.scan

import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun ScanPreview(
    modifier: Modifier = Modifier,
    previewView: PreviewView
) {
    BoxWithConstraints(modifier = modifier) {
        val scanFraction = 0.7f
        val stroke = 2.dp
        val scanSize = maxWidth * scanFraction
        val frameSize = scanSize + stroke * 2
        val radius = 8.dp

        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        Canvas(modifier = Modifier.matchParentSize()) {
            val overlayColor = Color(0xAA000000)
            drawRect(overlayColor)

            val sizePx = size.width * scanFraction
            val topLeft = Offset(
                (size.width - sizePx) / 2f,
                (size.height - sizePx) / 2f
            )

            drawRoundRect(
                color = Color.Transparent,
                topLeft = topLeft,
                size = Size(sizePx, sizePx),
                cornerRadius = CornerRadius(radius.toPx()),
                blendMode = BlendMode.Clear
            )
        }

        Image(
            painter = painterResource(id = R.drawable.crop_frame),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(frameSize)
        )
    }
}