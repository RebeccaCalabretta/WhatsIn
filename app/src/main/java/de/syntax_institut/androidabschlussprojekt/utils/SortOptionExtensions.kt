package de.syntax_institut.androidabschlussprojekt.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.helper.SortOption

@Composable
fun SortOption.toUiLabel(): String = when (this) {
    SortOption.NAME_ASC -> stringResource(R.string.sort_alphabetical_asc)
    SortOption.NAME_DESC -> stringResource(R.string.sort_alphabetical_desc)
    SortOption.DATE_NEWEST_FIRST -> stringResource(R.string.sort_newest_first)
    SortOption.DATE_OLDEST_FIRST -> stringResource(R.string.sort_oldest_first)
}