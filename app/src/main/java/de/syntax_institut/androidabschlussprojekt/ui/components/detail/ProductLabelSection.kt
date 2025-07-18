package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme

@Composable
fun ProductLabelSection(
    labels: List<String>
) {
    if (labels.isNotEmpty()) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                labels.forEach { label ->
                    LabelItem(text = label)
                }
            }
        }
    }
}

@Composable
fun LabelItem(text: String) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview(name = "ProductLabelSection – Light", showBackground = true)
@Preview(name = "ProductLabelSection – Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductLabelSectionPreview() {
    AndroidAbschlussprojektTheme {
        ProductLabelSection(
            labels = listOf("en:vegan", "en:organic", "en:fair-trade"))
    }
}