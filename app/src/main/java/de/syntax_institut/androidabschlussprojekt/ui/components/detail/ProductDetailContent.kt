package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.utils.detail.DetailDisplayHelper
import de.syntax_institut.androidabschlussprojekt.utils.hasAnyValue

@Composable
fun ProductDetailContent(
    ingredientsText: String?,
    nutriments: Nutriments,
    additivesTags: List<String>,
    allergensTags: List<String>,
    nutriScore: String?,
    labelsTags: List<String>?,
    selectedLanguage: String
) {
    val context = LocalContext.current

    val hasIngredients = !ingredientsText.isNullOrBlank()
    val hasNutriments = nutriments.hasAnyValue
    val hasAdditives = additivesTags.any { it.isNotBlank() }
    val hasAllergens = allergensTags.any { it.isNotBlank() }

    val displayNutriScore = DetailDisplayHelper.getDisplayNutriScore(nutriScore, labelsTags, context)
    val hasNutriScore = !nutriScore.isNullOrBlank() && displayNutriScore != context.getString(R.string.no_data)

    val noDetailsAvailable =
        !hasIngredients &&
                !hasNutriments &&
                !hasAdditives &&
                !hasAllergens &&
                !hasNutriScore

    if (noDetailsAvailable) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.no_details_available),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.weight(2f))
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (hasIngredients) IngredientsCard(ingredientsText)
        if (hasNutriments) NutrimentsCard(nutriments)
        if (hasAdditives) AdditivesCard(additivesTags, selectedLanguage)
        if (hasAllergens) AllergensCard(allergensTags, selectedLanguage)
        if (hasNutriScore) NutriScoreCard(nutriScore, labelsTags)
        Spacer(modifier = Modifier.height(20.dp))
    }
}