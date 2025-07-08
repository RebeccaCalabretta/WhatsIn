package de.syntax_institut.androidabschlussprojekt.ui.components.collection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.helper.SortOption

@Composable
fun CollectionHeader(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    sortOption: SortOption,
    onSortOptionSelected: (SortOption) -> Unit,
    showSearch: Boolean,
    onToggleSearch: () -> Unit,
    onCollapseSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Suche",
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

        SortDropdown(
            currentOption = sortOption,
            onOptionSelected = onSortOptionSelected,
            labelOnly = showSearch,
            onCollapseSearch = onCollapseSearch
        )
    }
}