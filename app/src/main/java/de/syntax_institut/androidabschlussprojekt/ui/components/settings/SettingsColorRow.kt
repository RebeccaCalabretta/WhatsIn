package de.syntax_institut.androidabschlussprojekt.ui.components.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.helper.AppColorScheme
import de.syntax_institut.androidabschlussprojekt.ui.theme.BlueButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.MintButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.OrangeButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.PistachioButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.PurpleButtonGradientLight

@Composable
fun SettingsColorRow(
    label: String,
    colorScheme: AppColorScheme,
    onClick: () -> Unit
) {
    SettingsRow(onClick = onClick) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.weight(1f))
        Canvas(
            modifier = Modifier.size(24.dp).clip(CircleShape)
        ) {
            drawCircle(
                brush = when (colorScheme) {
                    AppColorScheme.Orange -> OrangeButtonGradientLight
                    AppColorScheme.Mint -> MintButtonGradientLight
                    AppColorScheme.Green -> PistachioButtonGradientLight
                    AppColorScheme.Blue -> BlueButtonGradientLight
                    AppColorScheme.Lilac -> PurpleButtonGradientLight
                }
            )
        }
    }
}