package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.navigation.FilterRoute
import de.syntax_institut.androidabschlussprojekt.ui.components.settings.LanguageDropdown
import de.syntax_institut.androidabschlussprojekt.ui.components.settings.SelectColorSchemeDialog
import de.syntax_institut.androidabschlussprojekt.ui.components.settings.SettingsColorRow
import de.syntax_institut.androidabschlussprojekt.ui.components.settings.SettingsNavigationRow
import de.syntax_institut.androidabschlussprojekt.ui.components.settings.SettingsToggleRow
import de.syntax_institut.androidabschlussprojekt.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = koinViewModel(),
) {
    val isDarkmode by settingsViewModel.isDarkmode.collectAsState()
    val selectedLanguage by settingsViewModel.selectedLanguage.collectAsState()
    val appColorScheme by settingsViewModel.appColorScheme.collectAsState()
    var showColorDialog by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as? Activity

    val languages = listOf(
        "de" to stringResource(R.string.language_german),
        "en" to stringResource(R.string.language_english)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SettingsNavigationRow(
            label = stringResource(R.string.filter_options),
            onClick = { navController.navigate(FilterRoute) }
        )

        SettingsToggleRow(
            label = stringResource(R.string.dark_mode),
            checked = isDarkmode,
            onToggle = { settingsViewModel.toggleDarkmode() }
        )

        SettingsColorRow(
            label = stringResource(R.string.color_scheme),
            colorScheme = appColorScheme,
            onClick = { showColorDialog = true }
        )

        LanguageDropdown(
            selectedLanguage = selectedLanguage,
            languages = languages,
            onSelectLanguage = { langCode ->
                activity?.let { settingsViewModel.setLanguage(langCode, it) }
            }
        )
    }

    if (showColorDialog) {
        SelectColorSchemeDialog(
            current = appColorScheme,
            onDismiss = { showColorDialog = false },
            onSelect = {
                settingsViewModel.setAppColorScheme(it)
                showColorDialog = false
            }
        )
    }
}