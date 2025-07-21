package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R

@Composable
fun CorporationInfo(
    corporation: String?
) {
    if (!corporation.isNullOrBlank()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            if (corporation.isNotBlank()) {
                Text(
                    stringResource(R.string.belongs_to, corporation),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}