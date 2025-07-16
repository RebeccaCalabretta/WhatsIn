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
fun AnimatedOnboardingGradient(): Brush {
    val infiniteTransition = rememberInfiniteTransition(label = "onboarding_gradient")
    val anim = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_offset"
    ).value

    val waveY = 350f * sin(anim)
    val waveX = 180f * sin(anim * 0.7f + 1f)

    return Brush.linearGradient(
        colors = listOf(Mint80, Blue80),
        start = Offset(x = 0f + waveX, y = 0f + waveY),
        end = Offset(x = 1400f - waveX, y = 2100f - waveY)
    )
}