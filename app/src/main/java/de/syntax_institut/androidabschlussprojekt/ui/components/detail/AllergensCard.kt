package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.mapping.AllergenMapper
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard

@Composable
fun AllergensCard(
    allergensTags: List<String>,
    selectedLanguage: String
) {
    if (allergensTags.isNotEmpty()) {
        ExpandableCard(
            title = { Text(stringResource(R.string.allergens), style = MaterialTheme.typography.titleMedium) }
        ) {
            Text(
                allergensTags.joinToString { AllergenMapper.map(it, selectedLanguage) },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        DetailCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${stringResource(R.string.allergens)}: ",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    stringResource(R.string.no_data),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}