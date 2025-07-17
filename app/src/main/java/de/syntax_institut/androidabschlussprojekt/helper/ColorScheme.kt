package de.syntax_institut.androidabschlussprojekt.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R

enum class AppColorScheme {
    Mint,
    Green,
    Blue,
    Lilac,
    Orange
}

@Composable
fun getSchemeLabel(scheme: AppColorScheme): String {
    return when (scheme) {
        AppColorScheme.Mint   -> stringResource(R.string.scheme_mint)
        AppColorScheme.Green  -> stringResource(R.string.scheme_green)
        AppColorScheme.Blue   -> stringResource(R.string.scheme_blue)
        AppColorScheme.Lilac  -> stringResource(R.string.scheme_lilac)
        AppColorScheme.Orange -> stringResource(R.string.scheme_orange)
    }
}