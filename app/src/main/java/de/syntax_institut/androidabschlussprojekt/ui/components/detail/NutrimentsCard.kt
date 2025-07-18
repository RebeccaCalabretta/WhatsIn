package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard
import de.syntax_institut.androidabschlussprojekt.utils.formatNutriments
import de.syntax_institut.androidabschlussprojekt.utils.hasAnyValue

@Composable
fun NutrimentsCard(nutriments: Nutriments) {
    if (!nutriments.hasAnyValue) return
    val context = LocalContext.current
    val nutrientStrings = nutriments.formatNutriments(context)
    ExpandableCard(
        title = { Text(stringResource(R.string.nutriments_per_100g), style = MaterialTheme.typography.titleMedium) }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            nutrientStrings.forEach { line ->
                Text(line, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}