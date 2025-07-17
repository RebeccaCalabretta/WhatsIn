package de.syntax_institut.androidabschlussprojekt.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun animatedGradient(): Brush {
    val infiniteTransition = rememberInfiniteTransition(label = "onboarding_gradient")

    val animX = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave_offset_x"
    ).value

    val animY = infiniteTransition.animateFloat(
        initialValue = PI.toFloat(),
        targetValue = 3 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave_offset_y"
    ).value

    val waveY = 350f * sin(animY)
    val waveX = 180f * sin(animX * 0.7f + 1f)

    return Brush.linearGradient(
        colors = listOf(Mint80, Blue80),
        start = Offset(x = 0f + waveX, y = 0f + waveY),
        end = Offset(x = 1400f - waveX, y = 2100f - waveY)
    )
}