package de.syntax_institut.androidabschlussprojekt.ui.components.collection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.helper.SortOption
import de.syntax_institut.androidabschlussprojekt.utils.toUiLabel

@OptIn(ExperimentalMaterial3Api::class)
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

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        AssistChip(
            onClick = {
                if (labelOnly && onCollapseSearch != null) {
                    onCollapseSearch()
                    expanded = true
                } else {
                    expanded = true
                }
            },
            label = {
                if (!labelOnly) {
                    Text(stringResource(R.string.sort_by))
                }
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .menuAnchor()
                .then(
                    if (labelOnly) Modifier.size(36.dp)
                    else Modifier.height(36.dp)
                )
                .padding(start = 2.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.widthIn(min = 180.dp)
        ) {
            sortOptions.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(option.toUiLabel())
                            if (option == currentOption) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = stringResource(R.string.currently_selected),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}