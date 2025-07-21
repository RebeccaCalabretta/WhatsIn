package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.components.onboarding.OnboardingFooter
import de.syntax_institut.androidabschlussprojekt.ui.components.onboarding.SkipButton
import de.syntax_institut.androidabschlussprojekt.ui.components.onboarding.SlideContent
import de.syntax_institut.androidabschlussprojekt.ui.components.onboarding.onboardingSlides
import de.syntax_institut.androidabschlussprojekt.ui.theme.animatedGradient
import de.syntax_institut.androidabschlussprojekt.utils.SetSystemBarsColor
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    isDarkMode: Boolean,
    onFinish: () -> Unit,
    isColorSchemeLoaded: Boolean
) {
    SetSystemBarsColor(isDarkMode, transparent = true)

    val slides = onboardingSlides
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
                modifier = Modifier.weight(1f),
                pageSpacing = 32.dp
            ) { page ->
                SlideContent(slide = slides[page])
            }

            OnboardingFooter(
                pagerState = pagerState,
                slidesSize = slides.size,
                isColorSchemeLoaded = isColorSchemeLoaded,
                onNextOrFinish = {
                    if (pagerState.currentPage < slides.lastIndex) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        onFinish()
                    }
                }
            )
        }

        SkipButton(
            onClick = onFinish,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}