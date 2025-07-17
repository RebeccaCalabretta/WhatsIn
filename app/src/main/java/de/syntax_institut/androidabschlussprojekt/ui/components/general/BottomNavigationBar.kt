package de.syntax_institut.androidabschlussprojekt.ui.components.general

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import de.syntax_institut.androidabschlussprojekt.navigation.TabItem
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme

@Composable
fun BottomNavigationBar(
    selectedTabItem: TabItem,
    onSelectedTabItem: (TabItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
    ) {
        TabItem.entries
            .filter { it != TabItem.Scan }
            .forEach { item ->
                NavigationBarItem(
                    selected = selectedTabItem == item,
                    onClick = { onSelectedTabItem(item) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = item.label
                        )
                    },
                    label = { Text(item.label) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = Color.Transparent                    )
                )
            }
    }
}

@Preview(name = "BottomNavigationBar – Light", showBackground = true)
@Preview(name = "BottomNavigationBar – Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BottomNavigationBarPreview() {
    AndroidAbschlussprojektTheme {
        BottomNavigationBar(
            selectedTabItem = TabItem.Food,
            onSelectedTabItem = {}
        )
    }
}