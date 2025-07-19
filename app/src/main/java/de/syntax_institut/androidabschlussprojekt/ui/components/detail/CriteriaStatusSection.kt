package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.mapping.AdditiveMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.AllergenMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.BrandMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.CorporationMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.CountryMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.IngredientMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.NutriScoreMapper
import de.syntax_institut.androidabschlussprojekt.helper.FilterType
import de.syntax_institut.androidabschlussprojekt.model.FilterViolation
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard

@Composable
fun CriteriaStatusSection(
    filterViolations: List<FilterViolation>,
    selectedLanguage: String
) {
    if (filterViolations.isEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = stringResource(R.string.criteria_met),
                    tint = Color(0xFF4CAF50)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(R.string.criteria_met),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    } else {
        ExpandableCard(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = stringResource(R.string.criteria_not_met),
                        tint = Color(0xFFF44336)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(R.string.criteria_not_met),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            cardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column {
                filterViolations.forEach { violation ->
                    val mappedValue = when (violation.type) {
                        FilterType.LABELS -> violation.value
                            ?.split(",")
                            ?.joinToString(", ") {
                                LabelMapper.map(it.trim(), selectedLanguage) ?: it.trim()
                            }

                        FilterType.COUNTRIES -> violation.value
                            ?.split(",")
                            ?.joinToString(", ") {
                                CountryMapper.map(it.trim(), selectedLanguage)
                            }

                        FilterType.ALLERGENS -> violation.value
                            ?.split(",")
                            ?.joinToString(", ") {
                                AllergenMapper.map(it.trim(), selectedLanguage)
                            }

                        FilterType.INGREDIENTS -> violation.value
                            ?.split(",")
                            ?.joinToString(", ") {
                                IngredientMapper.map(it.trim(), selectedLanguage)
                            }

                        FilterType.ADDITIVES -> violation.value
                            ?.split(",")
                            ?.joinToString(", ") {
                                AdditiveMapper.map(it.trim(), selectedLanguage)
                            }

                        FilterType.BRANDS -> violation.value
                            ?.split(",")
                            ?.joinToString(", ") { BrandMapper.map(it.trim()) }

                        FilterType.NUTRISCORE -> violation.value
                            ?.split(",")
                            ?.joinToString(", ") {
                                NutriScoreMapper.map(it.trim())?.uppercase() ?: "?"
                            }

                        FilterType.CORPORATIONS -> violation.value
                            ?.split(",")
                            ?.joinToString(", ") { CorporationMapper.map(it.trim()) ?: it.trim() }
                    }
                    Text(
                        text = "• " + stringResource(violation.resId, mappedValue ?: ""),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}