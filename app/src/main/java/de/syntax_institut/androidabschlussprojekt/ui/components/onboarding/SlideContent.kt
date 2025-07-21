package de.syntax_institut.androidabschlussprojekt.ui.components.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.screen.OnboardingSlide
import de.syntax_institut.androidabschlussprojekt.ui.theme.MichromaFont

@Composable
fun SlideContent(slide: OnboardingSlide) = Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = slide.title,
        style = MaterialTheme.typography.titleMedium,
        fontFamily = MichromaFont,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    slide.imageResId?.let { resId ->
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }

    slide.description?.takeIf { it.isNotBlank() }?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}