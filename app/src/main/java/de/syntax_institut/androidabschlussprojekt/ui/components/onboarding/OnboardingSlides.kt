package de.syntax_institut.androidabschlussprojekt.ui.components.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R

data class OnboardingSlide(
    val title: String,
    val description: String? = null,
    @DrawableRes val imageResId: Int? = null
)

val onboardingSlides: List<OnboardingSlide>
    @Composable
    get() = listOf(
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_1),
            imageResId = R.drawable.whatsin_schriftzug_mit_slogan
        ),
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_2),
            imageResId = R.drawable.ic_crop_free,
            description = stringResource(R.string.onboarding_description_2)
        ),
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_3),
            imageResId = R.drawable.ic_search_insights,
            description = stringResource(R.string.onboarding_description_3)
        ),
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_4),
            imageResId = R.drawable.ic_task_alt,
            description = stringResource(R.string.onboarding_description_4)
        ),
        OnboardingSlide(
            title = stringResource(R.string.onboarding_title_5),
            imageResId = R.drawable.ic_rocket_launch,
            description = stringResource(R.string.onboarding_description_5)
        )
    )