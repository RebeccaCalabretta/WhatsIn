package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard

@Composable
fun IngredientsCard(ingredientsText: String?) {
    if (ingredientsText.isNullOrBlank()) return

    ExpandableCard(
        title = { Text(stringResource(R.string.ingredients), style = MaterialTheme.typography.titleMedium) }
    ) {
        Text(ingredientsText, style = MaterialTheme.typography.bodyMedium)
    }
}