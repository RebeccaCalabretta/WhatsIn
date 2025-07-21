package de.syntax_institut.androidabschlussprojekt.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.components.general.CustomDropdownMenu

@Composable
fun LanguageDropdown(
    selectedLanguage: String,
    languages: List<Pair<String, String>>,
    onSelectLanguage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val shape = RoundedCornerShape(16.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant, shape)
            .clickable { expanded = !expanded }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(R.string.language),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = languages.firstOrNull { it.first == selectedLanguage }?.second ?: languages[0].second,
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }

    CustomDropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        options = languages,
        selectedOption = languages.firstOrNull { it.first == selectedLanguage },
        optionLabel = { Text(it.second) },
        onOptionSelected = { onSelectLanguage(it.first) },
        menuWidth = 200.dp,
        offsetY = 216.dp
    )
}