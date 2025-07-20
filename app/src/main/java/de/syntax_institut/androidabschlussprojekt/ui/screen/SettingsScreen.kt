package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.app.Activity
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.helper.AppColorScheme
import de.syntax_institut.androidabschlussprojekt.navigation.FilterRoute
import de.syntax_institut.androidabschlussprojekt.ui.components.settings.LanguageDropdown
import de.syntax_institut.androidabschlussprojekt.ui.components.settings.SelectColorSchemeDialog
import de.syntax_institut.androidabschlussprojekt.ui.components.settings.SettingsRow
import de.syntax_institut.androidabschlussprojekt.ui.theme.BlueButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.MintButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.OrangeButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.PistachioButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.PurpleButtonGradientLight
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
        SettingsRow(
            onClick = { navController.navigate(FilterRoute) }
        ) {
            Text(stringResource(R.string.filter_options), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        }

        SettingsRow {
            Text(stringResource(R.string.dark_mode), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkmode,
                onCheckedChange = { settingsViewModel.toggleDarkmode() }
            )
        }

        SettingsRow(
            onClick = { showColorDialog = true }
        ) {
            Text(stringResource(R.string.color_scheme), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Canvas(
                modifier = Modifier.size(24.dp).clip(CircleShape)
            ) {
                drawCircle(
                    brush = when (appColorScheme) {
                        AppColorScheme.Orange -> OrangeButtonGradientLight
                        AppColorScheme.Mint -> MintButtonGradientLight
                        AppColorScheme.Green -> PistachioButtonGradientLight
                        AppColorScheme.Blue -> BlueButtonGradientLight
                        AppColorScheme.Lilac -> PurpleButtonGradientLight
                    }
                )
            }
        }

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