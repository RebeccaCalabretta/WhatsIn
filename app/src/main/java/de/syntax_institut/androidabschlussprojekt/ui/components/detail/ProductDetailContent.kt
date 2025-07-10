package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.mapping.AdditiveMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.AllergenMapper
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard
import de.syntax_institut.androidabschlussprojekt.utils.formatNutriments
import de.syntax_institut.androidabschlussprojekt.utils.hasAnyValue

@Composable
fun ProductDetailContent(
    ingredientsText: String?,
    nutriments: Nutriments,
    additivesTags: List<String>,
    allergensTags: List<String>,
    nutriScore: String?,
    brand: String?,
    corporation: String?
) {
    val nutrientStrings = nutriments.formatNutriments()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!brand.isNullOrBlank() || !corporation.isNullOrBlank()) {
            DetailCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!brand.isNullOrBlank()) {
                        Text(brand, style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(Modifier.weight(1f))
                    if (!corporation.isNullOrBlank()) {
                        Text(stringResource(R.string.belongs_to), style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        if (!ingredientsText.isNullOrBlank()) {
            ExpandableCard(
                title = { Text(stringResource(R.string.ingredients), style = MaterialTheme.typography.titleMedium) }
            ) {
                Text(ingredientsText, style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            DetailCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${stringResource(R.string.ingredients)}: ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        stringResource(R.string.no_data),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

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

        if (additivesTags.isNotEmpty()) {
            ExpandableCard(
                title = { Text(stringResource(R.string.additives), style = MaterialTheme.typography.titleMedium) }
            ) {
                Text(
                    additivesTags.joinToString { AdditiveMapper.map(it) },
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
                        "${stringResource(R.string.additives)}: ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        stringResource(R.string.no_data),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        if (allergensTags.isNotEmpty()) {
            ExpandableCard(
                title = { Text(stringResource(R.string.allergens), style = MaterialTheme.typography.titleMedium) }
            ) {
                Text(
                    allergensTags.joinToString { AllergenMapper.map(it) },
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

        DetailCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${stringResource(R.string.nutri_score)}: ",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    nutriScore?.trim()?.uppercase() ?: stringResource(R.string.unknown),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}