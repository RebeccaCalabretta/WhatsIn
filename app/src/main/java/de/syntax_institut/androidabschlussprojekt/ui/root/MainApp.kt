package de.syntax_institut.androidabschlussprojekt.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import de.syntax_institut.androidabschlussprojekt.navigation.AppStart
import de.syntax_institut.androidabschlussprojekt.ui.screen.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun MainApp() {
    var showSplash by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2500)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        AppStart()
    }
}