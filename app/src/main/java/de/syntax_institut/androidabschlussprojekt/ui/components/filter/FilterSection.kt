package de.syntax_institut.androidabschlussprojekt.ui.components.filter

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme

@Composable
fun FilterSection(
    title: String,
    items: List<String>,
    selectedItems: List<String> = emptyList(),
    onToggleItem: (String) -> Unit,
    labelMapper: (String) -> String = { it }

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                FilterChip(
                    selected = item in selectedItems,
                    onClick = { onToggleItem(item) },
                    label = { Text(labelMapper(item)) }
                )
            }
        }
    }
}

@Preview(
    name = "FilterSection – Light",
    showBackground = true
)
@Preview(
    name = "FilterSection – Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun FilterSectionPreview() {
    AndroidAbschlussprojektTheme {
        FilterSection(
            title = "Labels",
            items = listOf("Vegan", "Bio", "Fairtrade"),
            selectedItems = listOf("Vegan"),
            onToggleItem = {}
        )
    }
}
