package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.data.mapping.AdditiveMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.AllergenMapper
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard
import de.syntax_institut.androidabschlussprojekt.utils.formatNutriments

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!brand.isNullOrBlank() || !corporation.isNullOrBlank()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!brand.isNullOrBlank()) {
                    Text(brand, style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(Modifier.weight(1f))
                if (!corporation.isNullOrBlank()) {
                    Text("Gehört zu: $corporation", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        ExpandableCard(
            title = { Text("Inhaltsstoffe", style = MaterialTheme.typography.titleMedium) }
        ) {
            Text(ingredientsText ?: "Keine Angaben", style = MaterialTheme.typography.bodyMedium)
        }

        ExpandableCard(
            title = { Text("Nährwerte (pro 100g)", style = MaterialTheme.typography.titleMedium) }
        ) {
            Text(nutriments.formatNutriments(), style = MaterialTheme.typography.bodyMedium)
        }

        ExpandableCard(
            title = {
                Text(
                    "Zusatzstoffe, Allergene & Nutri-Score",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Zusatzstoffe: " + (
                            additivesTags.takeUnless { it.isEmpty() }
                                ?.joinToString { AdditiveMapper.map(it) }
                                ?: "Keine"
                            ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "Allergene: " + (
                            allergensTags.takeUnless { it.isEmpty() }
                                ?.joinToString { AllergenMapper.map(it) }
                                ?: "Keine"
                            ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "Nutri-Score: ${nutriScore?.trim()?.uppercase() ?: "Unbekannt"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}