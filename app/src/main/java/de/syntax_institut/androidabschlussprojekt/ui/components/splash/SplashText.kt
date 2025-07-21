package de.syntax_institut.androidabschlussprojekt.ui.components.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun SplashText() {
    Image(
        painter = painterResource(id = R.drawable.splash_text),
        contentDescription = null,
        modifier = Modifier
            .width(300.dp)
    )
}