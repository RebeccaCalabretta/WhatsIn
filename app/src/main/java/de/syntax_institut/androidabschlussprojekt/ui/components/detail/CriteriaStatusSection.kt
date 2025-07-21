package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
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
import de.syntax_institut.androidabschlussprojekt.model.FilterViolation
import de.syntax_institut.androidabschlussprojekt.ui.components.general.ExpandableCard

@Composable
fun CriteriaStatusSection(
    filterViolations: List<FilterViolation>,
    selectedLanguage: String
) {
    if (filterViolations.isEmpty()) {
        CriteriaCard()
    } else {
        ExpandableCard(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = stringResource(R.string.criteria_not_met),
                        tint = Color(0xFFFFC107)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(R.string.criteria_not_met),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                filterViolations.forEach { violation ->
                    CriteriaViolationItem(violation, selectedLanguage)
                }
            }
        }
    }
}