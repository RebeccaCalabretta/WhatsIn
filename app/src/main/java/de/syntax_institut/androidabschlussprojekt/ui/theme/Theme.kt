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

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val LightOrangeColorScheme = lightColorScheme(
    primary = Orange80,
    primaryContainer = Orange40,
    secondaryContainer = Orange20,
    surface = SurfaceLight,
    surfaceVariant = Orange10,
    background = BackgroundLight,
    onPrimary = Color.White,
    onPrimaryContainer = OnOrangeContainerLight,
    onSurface = Color(0xFF1C1B1F),
    onBackground = Color(0xFF1C1B1F)
)

val DarkOrangeColorScheme = darkColorScheme(
    primary = Orange80,
    primaryContainer = Orange60,
    surface = SurfaceDark,
    surfaceVariant = Orange40,
    background = Color.Black,
    onPrimary = Color.Black,
    onPrimaryContainer = OnOrangeContainerDark,
    onSurface = Color.White,
    onBackground = Color.White
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