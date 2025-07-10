package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard
import de.syntax_institut.androidabschlussprojekt.utils.formatNutriments
import de.syntax_institut.androidabschlussprojekt.utils.hasAnyValue

@Composable
fun NutrimentsCard(nutriments: Nutriments) {
    val nutrientStrings = nutriments.formatNutriments()

    if (nutriments.hasAnyValue) {
        ExpandableCard(
            title = { Text(stringResource(R.string.nutriments_per_100g), style = MaterialTheme.typography.titleMedium) }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                nutrientStrings.forEach { line ->
                    Text(line, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    } else {
        DetailCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${stringResource(R.string.nutriments_per_100g)}: ",
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