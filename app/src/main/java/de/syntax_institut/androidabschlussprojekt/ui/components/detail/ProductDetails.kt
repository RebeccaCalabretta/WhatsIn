package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.model.Product
import de.syntax_institut.androidabschlussprojekt.utils.detail.DetailDisplayHelper

@Composable
fun ProductDetails(
    product: Product,
    selectedLanguage: String,
    modifier: Modifier = Modifier
) {
    val displayLabels = DetailDisplayHelper.getDisplayLabels(product.labelsTags, selectedLanguage)

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ProductLabelSection(labels = displayLabels)

        ProductDetailContent(
            ingredientsText = product.ingredientsText,
            nutriments = product.nutriments,
            additivesTags = product.additivesTags,
            allergensTags = product.allergensTags,
            nutriScore = product.nutriScore,
            labelsTags = product.labelsTags,
            selectedLanguage = selectedLanguage
        )
    }
}