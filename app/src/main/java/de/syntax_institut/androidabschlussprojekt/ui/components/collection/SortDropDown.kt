package de.syntax_institut.androidabschlussprojekt.ui.components.collection

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.helper.SortOption
import de.syntax_institut.androidabschlussprojekt.ui.components.general.CustomDropdownMenu
import de.syntax_institut.androidabschlussprojekt.utils.collection.toUiLabel

@Composable
fun SortDropdown(
    currentOption: SortOption,
    onOptionSelected: (SortOption) -> Unit,
    labelOnly: Boolean = false,
    onCollapseSearch: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    val sortOptions = listOf(
        SortOption.DATE_NEWEST_FIRST,
        SortOption.DATE_OLDEST_FIRST,
        SortOption.NAME_ASC,
        SortOption.NAME_DESC
    )

    AssistChip(
        onClick = {
            if (labelOnly && onCollapseSearch != null) {
                onCollapseSearch()
                expanded = true
            } else {
                expanded = !expanded
            }
        },
        label = {
            if (!labelOnly) {
                Text(stringResource(R.string.sort_by))
            }
        },
        trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) },
        modifier = if (labelOnly) {
            Modifier
                .width(38.dp)
                .height(36.dp)
                .padding(start = 2.dp)
        } else {
            Modifier
                .height(36.dp)
                .padding(start = 2.dp)
        }
    )

    CustomDropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        options = sortOptions,
        selectedOption = currentOption,
        optionLabel = { Text(it.toUiLabel()) },
        onOptionSelected = onOptionSelected,
        menuWidth = 200.dp,
        offsetY = 36.dp
    )
}