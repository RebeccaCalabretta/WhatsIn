package de.syntax_institut.androidabschlussprojekt.ui.components.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun SplashText() {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp
    val density = LocalDensity.current
    val offsetY = with(density) { (screenHeightDp.dp * 0.20f).toPx() }

    Image(
        painter = painterResource(id = R.drawable.splash_text),
        contentDescription = null,
        modifier = Modifier
            .offset { IntOffset(0, offsetY.toInt()) }
            .width(300.dp)
    )
}