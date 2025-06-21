package de.syntax_institut.androidabschlussprojekt.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LightOrangeColorScheme = lightColorScheme(
    primary = Orange80,
    primaryContainer = Orange40,
    secondaryContainer = Orange20,
    surface = SurfaceLight,
    surfaceVariant = Orange10,
    background = BackgroundLight,
    onPrimary = Color.White,
    onPrimaryContainer = OnOrangeContainerLight,
    onSurface = OnSurfaceLight,
    onBackground = OnBackgroundLight
)

val DarkOrangeColorScheme = darkColorScheme(
    primary = Orange80,
    primaryContainer = OrangeContainerDark,
    secondaryContainer = SecondaryContainerDark,
    surface = SurfaceDark,
    surfaceVariant = SecondaryContainerDark,
    background = Color.Black,
    onPrimary = Color.Black,
    onPrimaryContainer = OnOrangeContainerDark,
    onSurface = OnSurfaceDark,
    onBackground = OnBackgroundDark
)

@Composable
fun AndroidAbschlussprojektTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkOrangeColorScheme
        else -> LightOrangeColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}