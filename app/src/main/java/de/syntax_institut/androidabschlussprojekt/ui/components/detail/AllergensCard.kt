package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.mapping.AllergenMapper
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard


@Composable
fun AllergensCard(allergensTags: List<String>, selectedLanguage: String) {
    if (allergensTags.isEmpty() || allergensTags.all { it.isBlank() }) return
    ExpandableCard(
        title = { Text(stringResource(R.string.allergens), style = MaterialTheme.typography.titleMedium) }
    ) {
        Text(
            allergensTags.joinToString { AllergenMapper.map(it, selectedLanguage) },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}