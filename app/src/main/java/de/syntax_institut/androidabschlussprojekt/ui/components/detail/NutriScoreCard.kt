package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun NutriScoreCard(nutriScore: String?) {
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
}