package de.syntax_institut.androidabschlussprojekt.ui.components.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.screen.OnboardingSlide

val onboardingSlides: List<OnboardingSlide>
    @Composable
    get() = listOf(
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_1),
            imageResId = R.drawable.whatsin_schriftzug_mit_slogan
        ),
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_2),
            description = stringResource(R.string.onboarding_description_2)
        ),
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_3),
            description = stringResource(R.string.onboarding_description_3)
        ),
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_4),
            description = stringResource(R.string.onboarding_description_4)
        ),
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_5),
            description = stringResource(R.string.onboarding_description_5)
        )
    )