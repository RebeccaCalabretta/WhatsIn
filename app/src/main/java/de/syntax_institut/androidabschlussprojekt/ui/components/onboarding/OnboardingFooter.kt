package de.syntax_institut.androidabschlussprojekt.ui.components.onboarding

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.components.general.GeneralButton

@Composable
fun OnboardingFooter(
    pagerState: PagerState,
    slidesSize: Int,
    isColorSchemeLoaded: Boolean,
    onNextOrFinish: () -> Unit
) {
    PagerIndicator(
        pageCount = slidesSize,
        currentPage = pagerState.currentPage
    )

    GeneralButton(
        text = if (pagerState.currentPage == slidesSize - 1)
            stringResource(R.string.onboarding_button_finish)
        else
            stringResource(R.string.next),
        onClick = onNextOrFinish,
        enabled = pagerState.currentPage < slidesSize - 1 || isColorSchemeLoaded,
        modifier = Modifier.fillMaxWidth(0.5f)
    )
}