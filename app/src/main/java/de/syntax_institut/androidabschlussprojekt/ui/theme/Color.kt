package de.syntax_institut.androidabschlussprojekt.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Orange Glow – Basis
val Orange90 = Color(0xFFBA5A13)
val Orange80 = Color(0xFFF78C2A)
val Orange60 = Color(0xFFFFD1A3)
val Orange40 = Color(0xFFFAD4B0)
val Orange20 = Color(0xFFFBECDE)
val Orange10 = Color(0xFFFFF3E0)

val OrangeContainerDark = Orange90
val SecondaryContainerDark = Color(0xFF5F3107)

// Textfarben auf Cards
val OnOrangeContainerLight = Color(0xFF3B2200)
val OnOrangeContainerDark = Color.White

// Neutrale Farben
val BackgroundLight = Color(0xFFFFFCF8)
val SurfaceLight = Color.White
val SurfaceDark = Color(0xFF121212)

val OnSurfaceLight = Color(0xFF5C5C5C)
val OnSurfaceDark = Color(0xFFDBDBDB)
val OnBackgroundLight = Color(0xFF1C1B1F)
val OnBackgroundDark = Color.White

// FAB Gradients
val FabGradientLight = Brush.verticalGradient(
    colors = listOf(Orange60, Orange80)
)

val FabGradientDark = Brush.verticalGradient(
    colors = listOf(Orange80, Orange90)
)

val ButtonGradientBrush = Brush.verticalGradient(
    colors = listOf(Orange60, Orange80)
)