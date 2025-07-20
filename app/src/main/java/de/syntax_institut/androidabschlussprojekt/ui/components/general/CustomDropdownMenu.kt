package de.syntax_institut.androidabschlussprojekt.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun <DropdownItem> CustomDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    options: List<DropdownItem>,
    selectedOption: DropdownItem? = null,
    optionLabel: @Composable (DropdownItem) -> Unit,
    onOptionSelected: (DropdownItem) -> Unit,
    menuWidth: Dp = 200.dp,
    cornerRadius: Dp = 16.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 40.dp,
) {
    if (!expanded) return

    val density = LocalDensity.current
    val pxOffsetX = with(density) { offsetX.toPx() }.toInt()
    val pxOffsetY = with(density) { offsetY.toPx() }.toInt()

    Popup(
        alignment = Alignment.TopEnd,
        offset = IntOffset(pxOffsetX, pxOffsetY),
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true)
    ) {
        Card(
            modifier = Modifier
                .width(menuWidth)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(cornerRadius)),
            shape = RoundedCornerShape(cornerRadius)
        ) {
            Column {
                options.forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onOptionSelected(item)
                                onDismissRequest()
                            }
                            .background(
                                if (selectedOption != null && item == selectedOption)
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.07f)
                                else
                                    Color.Transparent
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            optionLabel(item)
                            if (selectedOption != null && item == selectedOption) {
                                Spacer(Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}