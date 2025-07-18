package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.utils.detail.DetailDisplayHelper

@Composable
fun NutriScoreCard(nutriScore: String?, labelsTags: List<String>?) {
    val context = LocalContext.current
    val displayNutriScore = DetailDisplayHelper.getDisplayNutriScore(nutriScore, labelsTags, context)

    DetailCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${stringResource(R.string.nutri_score)}: ",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = displayNutriScore,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}