package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.components.filter.FilterSection
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilterScreen(
    filterViewModel: FilterViewModel = koinViewModel()
) {
    val isLoading by filterViewModel.isLoading.collectAsState()
    val activeFilter by filterViewModel.activeFilter.collectAsState()
    val availableFilters by filterViewModel.availableFilters.collectAsState()

    val configs = remember(activeFilter, availableFilters) {
        filterViewModel.buildFilterConfigs()
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
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
                    onToggleItem = config.onToggleItem
                )
            }
        }
    }
}