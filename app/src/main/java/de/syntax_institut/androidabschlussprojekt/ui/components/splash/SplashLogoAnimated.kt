package de.syntax_institut.androidabschlussprojekt.ui.components.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun SplashLogoAnimated(
    alpha: Float,
    scale: Float
) {
    Image(
        painter = painterResource(id = R.drawable.splash_logo),
        contentDescription = null,
        modifier = Modifier
            .size(120.dp)
            .graphicsLayer(
                alpha = alpha,
                scaleX = scale,
                scaleY = scale
            )
    )
}