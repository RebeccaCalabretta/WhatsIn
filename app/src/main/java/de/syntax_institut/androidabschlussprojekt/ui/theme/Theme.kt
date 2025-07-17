package de.syntax_institut.androidabschlussprojekt.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import de.syntax_institut.androidabschlussprojekt.helper.AppColorScheme

lateinit var ButtonGradient: Brush

val LightOrangeColorScheme = lightColorScheme(
    primary = Orange80,
    primaryContainer = Orange40,
    secondary = Orange60,
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
    secondary = Orange90,
    secondaryContainer = SecondaryContainerDark,
    surface = SecondaryContainerDark,
    surfaceVariant = SecondaryContainerDark,
    background = Color.Black,
    onPrimary = Color.White,
    onPrimaryContainer = OnOrangeContainerDark,
    onSurface = OnSurfaceDark,
    onBackground = OnBackgroundDark
)

val LightMintColorScheme = lightColorScheme(
    primary = Mint80,
    primaryContainer = Mint40,
    secondary = Mint60,
    secondaryContainer = Mint20,
    surface = SurfaceLight,
    surfaceVariant = Mint10,
    background = BackgroundLight,
    onPrimary = Color.White,
    onPrimaryContainer = OnMintContainerLight,
    onSurface = OnSurfaceLight,
    onBackground = OnBackgroundLight
)

val DarkMintColorScheme = darkColorScheme(
    primary = Mint80,
    primaryContainer = MintContainerDark,
    secondary = Mint90,
    secondaryContainer = MintSecondaryContainerDark,
    surface = SurfaceDark,
    surfaceVariant = MintSecondaryContainerDark,
    background = Color.Black,
    onPrimary = Color.White,
    onPrimaryContainer = OnMintContainerDark,
    onSurface = OnSurfaceDark,
    onBackground = OnBackgroundDark
)

val LightPistachioColorScheme = lightColorScheme(
    primary = Pistachio80,
    primaryContainer = Pistachio40,
    secondary = Pistachio60,
    secondaryContainer = Pistachio20,
    surface = SurfaceLight,
    surfaceVariant = Pistachio10,
    background = BackgroundLight,
    onPrimary = Color.White,
    onPrimaryContainer = OnPistachioContainerLight,
    onSurface = OnSurfaceLight,
    onBackground = OnBackgroundLight
)

val DarkPistachioColorScheme = darkColorScheme(
    primary = Pistachio80,
    primaryContainer = PistachioContainerDark,
    secondary = Pistachio90,
    secondaryContainer = PistachioSecondaryContainerDark,
    surface = SurfaceDark,
    surfaceVariant = PistachioSecondaryContainerDark,
    background = Color.Black,
    onPrimary = Color.White,
    onPrimaryContainer = OnPistachioContainerDark,
    onSurface = OnSurfaceDark,
    onBackground = OnBackgroundDark
)

val LightBlueColorScheme = lightColorScheme(
    primary = Blue80,
    primaryContainer = Blue40,
    secondary = Blue60,
    secondaryContainer = Blue20,
    surface = SurfaceLight,
    surfaceVariant = Blue10,
    background = BackgroundLight,
    onPrimary = Color.White,
    onPrimaryContainer = OnBlueContainerLight,
    onSurface = OnSurfaceLight,
    onBackground = OnBackgroundLight
)

val DarkBlueColorScheme = darkColorScheme(
    primary = Blue80,
    primaryContainer = BlueContainerDark,
    secondary = Blue90,
    secondaryContainer = BlueSecondaryContainerDark,
    surface = SurfaceDark,
    surfaceVariant = BlueSecondaryContainerDark,
    background = Color.Black,
    onPrimary = Color.White,
    onPrimaryContainer = OnBlueContainerDark,
    onSurface = OnSurfaceDark,
    onBackground = OnBackgroundDark
)

val LightPurpleColorScheme = lightColorScheme(
    primary = Purple80,
    primaryContainer = Purple40,
    secondary = Purple60,
    secondaryContainer = Purple20,
    surface = SurfaceLight,
    surfaceVariant = Purple10,
    background = BackgroundLight,
    onPrimary = Color.White,
    onPrimaryContainer = OnPurpleContainerLight,
    onSurface = OnSurfaceLight,
    onBackground = OnBackgroundLight
)

val DarkPurpleColorScheme = darkColorScheme(
    primary = Purple80,
    primaryContainer = PurpleContainerDark,
    secondary = Purple90,
    secondaryContainer = PurpleSecondaryContainerDark,
    surface = SurfaceDark,
    surfaceVariant = PurpleSecondaryContainerDark,
    background = Color.Black,
    onPrimary = Color.White,
    onPrimaryContainer = OnPurpleContainerDark,
    onSurface = OnSurfaceDark,
    onBackground = OnBackgroundDark
)

@Composable
fun AndroidAbschlussprojektTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    appColorScheme: AppColorScheme = AppColorScheme.Lilac,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> when (appColorScheme) {
            AppColorScheme.Mint -> if (darkTheme) DarkMintColorScheme else LightMintColorScheme
            AppColorScheme.Green -> if (darkTheme) DarkPistachioColorScheme else LightPistachioColorScheme
            AppColorScheme.Blue -> if (darkTheme) DarkBlueColorScheme else LightBlueColorScheme
            AppColorScheme.Lilac -> if (darkTheme) DarkPurpleColorScheme else LightPurpleColorScheme
            AppColorScheme.Orange -> if (darkTheme) DarkOrangeColorScheme else LightOrangeColorScheme

        }
    }

    ButtonGradient = when (appColorScheme) {
        AppColorScheme.Mint -> if (darkTheme) MintButtonGradientDark else MintButtonGradientLight
        AppColorScheme.Green -> if (darkTheme) PistachioButtonGradientDark else PistachioButtonGradientLight
        AppColorScheme.Blue -> if (darkTheme) BlueButtonGradientDark else BlueButtonGradientLight
        AppColorScheme.Lilac -> if (darkTheme) PurpleButtonGradientDark else PurpleButtonGradientLight
        AppColorScheme.Orange -> if (darkTheme) OrangeButtonGradientDark else OrangeButtonGradientLight

    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
