package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.syntax_institut.androidabschlussprojekt.ui.theme.AnimatedOnboardingGradient

@Composable
fun OnboardingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AnimatedOnboardingGradient())
    )
}