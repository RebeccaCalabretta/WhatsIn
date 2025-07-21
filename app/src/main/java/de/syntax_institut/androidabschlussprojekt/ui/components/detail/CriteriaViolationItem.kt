package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.model.FilterViolation
import de.syntax_institut.androidabschlussprojekt.utils.mapViolationValue

@Composable
fun CriteriaViolationItem(
    violation: FilterViolation,
    selectedLanguage: String
) {
    val mappedValue = mapViolationValue(violation.type, violation.value, selectedLanguage)
    Text(
        text = "• " + stringResource(violation.resId, mappedValue ?: ""),
        style = MaterialTheme.typography.bodySmall
    )
}