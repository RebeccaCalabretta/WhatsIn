package de.syntax_institut.androidabschlussprojekt.ui.components.general

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import de.syntax_institut.androidabschlussprojekt.navigation.TabItem

@Composable
fun BottomNavigationBar(
    selectedTabItem: TabItem,
    onSelectedTabItem: (TabItem) -> Unit
) {
    NavigationBar {
        TabItem.entries.forEach { item ->
            NavigationBarItem(
                selected = selectedTabItem == item,
                onClick = { onSelectedTabItem(item) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors()
            )
        }
    }
}