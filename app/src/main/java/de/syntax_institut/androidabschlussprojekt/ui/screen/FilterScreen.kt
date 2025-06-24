package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.components.filter.FilterSection
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilterScreen(
    filterViewModel: FilterViewModel = koinViewModel()
) {
    val configs = filterViewModel.buildFilterConfigs()

    if (configs.isEmpty()) {
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            configs.forEach { config ->
                FilterSection(
                    title = config.title,
                    items = config.items,
                    selectedItems = config.selectedItems,
                    onToggleItem = { item ->
                        val updated = if (item in config.selectedItems) {
                            config.selectedItems - item
                        } else {
                            config.selectedItems + item
                        }
                            .distinct()
                            .filter { it.isNotBlank() }

                        config.onUpdate(updated)
                    }
                )
            }
        }
    }
}