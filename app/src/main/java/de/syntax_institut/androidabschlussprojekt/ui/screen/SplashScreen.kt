package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme
import de.syntax_institut.androidabschlussprojekt.ui.theme.MintButtonGradientLight
import kotlinx.coroutines.launch

@Composable
fun SplashScreen() {
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        launch {
            alpha.animateTo(
                1f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
        }

        launch {
            repeat(2) {
                scale.animateTo(1.2f, tween(1000, easing = FastOutSlowInEasing))
                scale.animateTo(1f, tween(1000, easing = FastOutSlowInEasing))
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = MintButtonGradientLight),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer(
                    alpha = alpha.value,
                    scaleX = scale.value,
                    scaleY = scale.value
                )
        )
    }
}

@Preview(name = "SplashScreen – Light", showBackground = true)
@Preview(name = "SplashScreen – Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPreview() {
    AndroidAbschlussprojektTheme {
        SplashScreen()
    }
}