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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.components.collection.SearchBar
import de.syntax_institut.androidabschlussprojekt.ui.components.filter.FilterSection
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilterScreen(
    filterViewModel: FilterViewModel = koinViewModel(),
    settingsViewModel: SettingsViewModel = koinViewModel()
) {
    val isLoading by filterViewModel.isLoading.collectAsState()
    val activeFilter by filterViewModel.activeFilter.collectAsState()

    val searchText by filterViewModel.searchText.collectAsState()
    val language = settingsViewModel.selectedLanguage.collectAsState().value

    val configs = remember(activeFilter, searchText, language) {
        filterViewModel.buildFilterConfigs(searchText, language)
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
                onSearchTextChange = { filterViewModel.updateSearchText(it) },
                modifier = Modifier.fillMaxWidth()
            )

            configs.forEach { config ->
                FilterSection(
                    title = stringResource(config.titleRes),
                    items = config.items,
                    selectedItems = config.selectedItems,
                    onToggleItem = config.onToggleItem
                )
            }
        }
    }
}