package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.components.general.GeneralButton
import de.syntax_institut.androidabschlussprojekt.ui.theme.MichromaFont
import de.syntax_institut.androidabschlussprojekt.ui.theme.Mint20
import de.syntax_institut.androidabschlussprojekt.ui.theme.Mint60
import de.syntax_institut.androidabschlussprojekt.ui.theme.animatedGradient
import kotlinx.coroutines.launch

data class OnboardingSlide(
    val title: String,
    val description: String? = null,
    @DrawableRes val imageResId: Int? = null
)

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    isColorSchemeLoaded: Boolean
) {

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
            .background(animatedGradient())

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
                val slide = slides[page]

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = slides[page].title,
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
                            modifier = Modifier
                                .padding(bottom = 24.dp)
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
            }

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(slides.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 16.dp else 10.dp)
                            .padding(2.dp)
                            .background(
                                if (isSelected) Mint60 else Color.White,
                                shape = CircleShape
                            )
                    )
                }
            }

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