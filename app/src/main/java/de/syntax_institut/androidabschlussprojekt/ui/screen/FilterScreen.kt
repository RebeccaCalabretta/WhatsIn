package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.ui.components.filter.FilterHeader
import de.syntax_institut.androidabschlussprojekt.ui.components.filter.FilterSection
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilterScreen(
    snackbarHostState: SnackbarHostState,
    filterViewModel: FilterViewModel = koinViewModel(),
    settingsViewModel: SettingsViewModel = koinViewModel()
) {
    val isLoading by filterViewModel.isLoading.collectAsState()
    val activeFilter by filterViewModel.activeFilter.collectAsState()
    val searchText by filterViewModel.searchText.collectAsState()
    val filterError by filterViewModel.filterError.collectAsState()
    val language by settingsViewModel.selectedLanguage.collectAsState()

    var showSearch by remember { mutableStateOf(false) }

    val allFiltersChip = stringResource(R.string.all)
    val configs = remember(activeFilter, searchText, language) {
        filterViewModel.buildFilterConfigs(searchText, language, allFiltersChip)
    }

    val errorMessage = filterError?.let { stringResource(it.messageRes) }

    if (errorMessage != null) {
        LaunchedEffect(errorMessage) {
            snackbarHostState.showSnackbar(errorMessage)
            filterViewModel.clearFilterError()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                FilterHeader(
                    searchText = searchText,
                    onSearchTextChange = { filterViewModel.updateSearchText(it) },
                    showSearch = showSearch,
                    onToggleSearch = { showSearch = !showSearch },
                    onResetFilters = { filterViewModel.resetAllFilters() }
                )

                configs.forEach { config ->
                    FilterSection(
                        title = stringResource(config.titleRes),
                        items = config.items,
                        selectedItems = config.selectedItems,
                        onToggleItem = config.onToggleItem,
                        labelMapper = config.labelMapper
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}