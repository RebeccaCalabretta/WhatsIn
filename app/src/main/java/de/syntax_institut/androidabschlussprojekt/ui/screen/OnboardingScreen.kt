package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.components.general.GeneralButton
import de.syntax_institut.androidabschlussprojekt.ui.components.onboarding.PagerIndicator
import de.syntax_institut.androidabschlussprojekt.ui.components.onboarding.SlideContent
import de.syntax_institut.androidabschlussprojekt.ui.theme.Mint20
import de.syntax_institut.androidabschlussprojekt.ui.theme.animatedGradient
import de.syntax_institut.androidabschlussprojekt.utils.SetSystemBarsColor
import kotlinx.coroutines.launch

data class OnboardingSlide(
    val title: String,
    val description: String? = null,
    @DrawableRes val imageResId: Int? = null
)

@Composable
fun OnboardingScreen(
    isDarkMode: Boolean,
    onFinish: () -> Unit,
    isColorSchemeLoaded: Boolean
) {
    SetSystemBarsColor(isDarkMode, transparent = true)

    val slides = listOf(
        OnboardingSlide(
            title = stringResource(id = R.string.onboarding_title_1),
            description = null,
            imageResId = R.drawable.whatsin_schriftzug_mit_slogan
        ),
        OnboardingSlide(
            title = stringResource(id = R.string.onboarding_title_2),
            description = stringResource(id = R.string.onboarding_description_2)
        ),
        OnboardingSlide(
            title = stringResource(id = R.string.onboarding_title_3),
            description = stringResource(id = R.string.onboarding_description_3)
        ),
        OnboardingSlide(
            title = stringResource(id = R.string.onboarding_title_4),
            description = stringResource(id = R.string.onboarding_description_4)
        ),
        OnboardingSlide(
            title = stringResource(id = R.string.onboarding_title_5),
            description = stringResource(id = R.string.onboarding_description_5)
        )
    )

    val pagerState = rememberPagerState(pageCount = { slides.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = animatedGradient())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                SlideContent(slide = slides[page])
            }

            PagerIndicator(
                pageCount = slides.size,
                currentPage = pagerState.currentPage
            )

            GeneralButton(
                text = if (pagerState.currentPage == slides.lastIndex)
                    stringResource(R.string.onboarding_button_finish)
                else
                    stringResource(R.string.next),
                onClick = {
                    if (pagerState.currentPage < slides.lastIndex) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onFinish()
                    }
                },
                enabled = pagerState.currentPage < slides.lastIndex || isColorSchemeLoaded,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
        }

        TextButton(
            onClick = { onFinish() },
            enabled = true,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 40.dp, end = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.skip),
                style = MaterialTheme.typography.titleMedium,
                color = Mint20
            )
        }
    }
}