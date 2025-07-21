package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard
import de.syntax_institut.androidabschlussprojekt.utils.formatNutrientPairs
import de.syntax_institut.androidabschlussprojekt.utils.hasAnyValue

@Composable
fun NutrimentsCard(nutriments: Nutriments) {
    if (!nutriments.hasAnyValue) return
    val context = LocalContext.current
    val nutrientPairs = nutriments.formatNutrientPairs(context)

    ExpandableCard(
        title = {
            Text(
                text = stringResource(R.string.nutriments_per_100g),
                style = MaterialTheme.typography.titleMedium
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(start = 8.dp, end = 20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            nutrientPairs.forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                    )
                }
            }
        }
    }
}