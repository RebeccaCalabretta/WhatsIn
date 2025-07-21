package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.components.splash.SplashLogoAnimated
import de.syntax_institut.androidabschlussprojekt.ui.components.splash.SplashText
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme
import de.syntax_institut.androidabschlussprojekt.ui.theme.MintButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.utils.SetSystemBarsColor
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(isDarkmode: Boolean) {
    SetSystemBarsColor(isDarkmode, transparent = true)

    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        launch {
            alpha.animateTo(1f, tween(800, easing = FastOutSlowInEasing))
        }

        launch {
            repeat(2) {
                scale.animateTo(1.2f, tween(800, easing = FastOutSlowInEasing))
                scale.animateTo(1f, tween(800, easing = FastOutSlowInEasing))
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = MintButtonGradientLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.4f))

            SplashLogoAnimated(
                alpha = alpha.value,
                scale = scale.value
            )

            Spacer(modifier = Modifier.height(40.dp))

            SplashText()

            Spacer(modifier = Modifier.weight(0.4f))
        }
    }
}

@Preview(name = "SplashScreen – Light", showBackground = true)
@Preview(name = "SplashScreen – Dark", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPreview() {
    AndroidAbschlussprojektTheme {
        SplashScreen(isDarkmode = false)
    }
}