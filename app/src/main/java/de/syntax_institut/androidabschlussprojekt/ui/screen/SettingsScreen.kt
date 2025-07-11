package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.navigation.FilterRoute
import de.syntax_institut.androidabschlussprojekt.ui.components.settings.LanguageDropdown
import de.syntax_institut.androidabschlussprojekt.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = koinViewModel(),
) {
    val isDarkmode by settingsViewModel.isDarkmode.collectAsState()
    val selectedLanguage by settingsViewModel.selectedLanguage.collectAsState()

    val context = LocalContext.current
    val activity = context as? Activity

    val languages = listOf(
        "de" to stringResource(R.string.language_german),
        "en" to stringResource(R.string.language_english)
    )

    val rowHeight = 56.dp
    val cornerRadius = 16.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(cornerRadius)
                )
                .clickable { navController.navigate(FilterRoute) }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.filter_options), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rowHeight)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(cornerRadius)
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.dark_mode), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkmode,
                onCheckedChange = { settingsViewModel.toggleDarkmode() }
            )
        }

        LanguageDropdown(
            selectedLanguage = selectedLanguage,
            languages = languages,
            onSelectLanguage = { langCode ->
                activity?.let { settingsViewModel.setLanguage(langCode, it) }
            },
            rowHeight = rowHeight,
            cornerRadius = cornerRadius
        )
    }
}