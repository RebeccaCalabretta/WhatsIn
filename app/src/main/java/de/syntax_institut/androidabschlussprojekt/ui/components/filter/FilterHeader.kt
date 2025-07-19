package de.syntax_institut.androidabschlussprojekt.ui.components.filter

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.components.general.SearchBar
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme

@Composable
fun FilterHeader(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    showSearch: Boolean,
    onToggleSearch: () -> Unit,
    onResetFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.search),
            modifier = Modifier
                .size(24.dp)
                .clickable { onToggleSearch() }
        )

        if (showSearch) {
            Spacer(modifier = Modifier.width(4.dp))

            SearchBar(
                searchText = searchText,
                onSearchTextChange = onSearchTextChange,
                modifier = Modifier.weight(1f)
            )
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        if (!showSearch) {
            TextButton(
                onClick = onResetFilters,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(stringResource(R.string.reset_filters))
            }
        }
    }
}

@Preview(name = "FilterHeader – Light", showBackground = true)
@Preview(name = "FilterHeader – Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FilterHeaderPreview() {
    AndroidAbschlussprojektTheme {
        FilterHeader(
            searchText = "",
            onSearchTextChange = {},
            showSearch = false,
            onToggleSearch = {},
            onResetFilters = {},
            modifier = Modifier.padding(16.dp)
        )

    }
}