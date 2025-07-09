package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.data.mapping.AdditiveMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.AllergenMapper
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.utils.formatNutriments

@Composable
fun ProductDetailContent(
    ingredientsText: String?,
    nutriments: Nutriments,
    additivesTags: List<String>,
    allergensTags: List<String>,
    nutriScore: String?,
    corporation: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Inhaltsstoffe:", style = MaterialTheme.typography.titleMedium)
                Text(
                    ingredientsText ?: "Keine Angaben",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nährwerte (pro 100g):", style = MaterialTheme.typography.titleMedium)
                Text(
                    nutriments.formatNutriments(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Zusatzstoffe:", style = MaterialTheme.typography.titleMedium)
                Text(
                    additivesTags
                        .takeUnless { it.isEmpty() }
                        ?.joinToString { AdditiveMapper.map(it) }
                        ?: "Keine",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text("Allergene:", style = MaterialTheme.typography.titleMedium)
                Text(
                    allergensTags
                        .takeUnless { it.isEmpty() }
                        ?.joinToString { AllergenMapper.map(it) }
                        ?: "Keine",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    "Nutri-Score: ${nutriScore?.trim()?.uppercase() ?: "Unbekannt"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (!corporation.isNullOrBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Konzern: $corporation", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}