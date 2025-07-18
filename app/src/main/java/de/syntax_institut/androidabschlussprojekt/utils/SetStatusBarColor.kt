package de.syntax_institut.androidabschlussprojekt.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetSystemBarsColor(
    isDarkMode: Boolean,
    transparent: Boolean = false
) {
    val view = LocalView.current
    val color = if (transparent)
        android.graphics.Color.TRANSPARENT
    else
        MaterialTheme.colorScheme.background.toArgb()
    SideEffect {
        val window = (view.context as? android.app.Activity)?.window ?: return@SideEffect
        window.statusBarColor = color
        window.navigationBarColor = color
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = !isDarkMode
        WindowInsetsControllerCompat(window, view).isAppearanceLightNavigationBars = !isDarkMode
    }
}