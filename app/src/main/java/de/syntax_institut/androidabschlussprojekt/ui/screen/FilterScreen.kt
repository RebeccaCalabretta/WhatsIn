package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.SearchBar
import de.syntax_institut.androidabschlussprojekt.ui.components.filter.FilterSection
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilterScreen(
    filterViewModel: FilterViewModel = koinViewModel()
) {
    val isLoading by filterViewModel.isLoading.collectAsState()
    val activeFilter by filterViewModel.activeFilter.collectAsState()

    var searchText by remember { mutableStateOf("") }

    val configs = remember(activeFilter) {
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            SearchBar(
                searchText =searchText,
                onSearchTextChange = { searchText = it },
                modifier = Modifier.fillMaxWidth()
            )

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