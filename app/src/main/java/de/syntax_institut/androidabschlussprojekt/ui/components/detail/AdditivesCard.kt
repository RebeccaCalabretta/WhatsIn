package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.mapping.AdditiveMapper
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard

@Composable
fun AdditivesCard(additivesTags: List<String>, selectedLanguage: String) {
    if (additivesTags.all { it.isBlank() }) return

    ExpandableCard(
        title = { Text(stringResource(R.string.additives), style = MaterialTheme.typography.titleMedium) }
    ) {
        Text(
            additivesTags.joinToString { AdditiveMapper.map(it, selectedLanguage) },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}