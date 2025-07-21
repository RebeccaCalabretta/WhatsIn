package de.syntax_institut.androidabschlussprojekt.ui.components.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.theme.MichromaFont

@Composable
fun SlideContent(slide: OnboardingSlide) = Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Spacer(modifier = Modifier.weight(0.6f))

    Text(
        text = slide.title,
        style = MaterialTheme.typography.titleLarge,
        fontFamily = MichromaFont,
        textAlign = TextAlign.Center,
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )

    Spacer(modifier = Modifier.weight(0.2f))

    slide.imageResId?.let { resId ->
        val imageModifier = if (slide.title == stringResource(R.string.onboarding_title_1)) {
            Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        } else {
            Modifier
                .size(100.dp)
                .padding(bottom = 24.dp)
        }

        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = imageModifier
        )
    }

    Spacer(modifier = Modifier.weight(0.2f))

    slide.description?.takeIf { it.isNotBlank() }?.let {
        Text(
            text = it,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }

    Spacer(modifier = Modifier.weight(0.5f))
}