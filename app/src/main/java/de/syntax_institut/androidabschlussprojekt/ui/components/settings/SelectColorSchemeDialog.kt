package de.syntax_institut.androidabschlussprojekt.ui.components.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.helper.AppColorScheme
import de.syntax_institut.androidabschlussprojekt.helper.getSchemeLabel
import de.syntax_institut.androidabschlussprojekt.ui.theme.BlueButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.MintButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.OrangeButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.PistachioButtonGradientLight
import de.syntax_institut.androidabschlussprojekt.ui.theme.PurpleButtonGradientLight

@Composable
fun SelectColorSchemeDialog(
    current: AppColorScheme,
    onDismiss: () -> Unit,
    onSelect: (AppColorScheme) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.select_color_scheme)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AppColorScheme.entries.forEach { scheme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSelect(scheme) }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Canvas(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        ) {
                            drawCircle(
                                brush = when (scheme) {
                                    AppColorScheme.Orange -> OrangeButtonGradientLight
                                    AppColorScheme.Mint -> MintButtonGradientLight
                                    AppColorScheme.Green -> PistachioButtonGradientLight
                                    AppColorScheme.Blue -> BlueButtonGradientLight
                                    AppColorScheme.Lilac -> PurpleButtonGradientLight
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = getSchemeLabel(
                                scheme),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        if (scheme == current) {
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = stringResource(R.string.active),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}