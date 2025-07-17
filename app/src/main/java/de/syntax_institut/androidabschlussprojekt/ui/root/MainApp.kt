package de.syntax_institut.androidabschlussprojekt.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import de.syntax_institut.androidabschlussprojekt.navigation.AppStart
import de.syntax_institut.androidabschlussprojekt.ui.screen.OnboardingScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.SplashScreen
import de.syntax_institut.androidabschlussprojekt.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainApp(
    settingsViewModel: SettingsViewModel = koinViewModel()
) {
    var showSplash by rememberSaveable { mutableStateOf(true) }
    val onboardingCompleted by settingsViewModel.onboardingCompleted.collectAsState()
    val isColorSchemeLoaded by settingsViewModel.isColorSchemeLoaded.collectAsState()

    LaunchedEffect(Unit) {
        delay(3600)
        showSplash = false
    }
    when {
        showSplash -> SplashScreen()
        !onboardingCompleted -> OnboardingScreen(
            onFinish = {
                settingsViewModel.setOnboardingCompleted(true)
            },
            isColorSchemeLoaded = isColorSchemeLoaded
        )
        else -> AppStart()
    }
}