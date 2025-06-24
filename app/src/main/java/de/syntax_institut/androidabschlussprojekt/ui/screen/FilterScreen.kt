package de.syntax_institut.androidabschlussprojekt.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.ui.components.filter.FilterSection
import de.syntax_institut.androidabschlussprojekt.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FilterScreen(
    settingsViewModel: SettingsViewModel = koinViewModel()
) {
    val activeFilter = settingsViewModel.activeFilter.collectAsState()

    val availableIngredients = listOf("Palmöl", "Zucker", "Soja")
    val availableAllergens = listOf("Gluten", "Laktose", "Erdnuss")
    val availableAdditives = listOf("E102", "E202", "E330")
    val availableLabels = listOf("Vegan", "Bio", "Fairtrade")
    val availableNutriScores = listOf("A", "B", "C", "D", "E")
    val availableCountries = listOf("Deutschland", "Österreich", "Schweiz")
    val availableBrands = listOf("Nestlé", "Coca-Cola", "Dr. Oetker")
    val availableCorporations = listOf("Nestlé", "Unilever", "PepsiCo")

    val configs = listOf(
        FilterConfig("Inhaltsstoffe", availableIngredients, activeFilter.value.excludedIngredients) {
            settingsViewModel.updateFilter(activeFilter.value.copy(excludedIngredients = it))
        },
        FilterConfig("Allergene", availableAllergens, activeFilter.value.excludedAllergens) {
            settingsViewModel.updateFilter(activeFilter.value.copy(excludedAllergens = it))
        },
        FilterConfig("Additives", availableAdditives, activeFilter.value.excludedAdditives) {
            settingsViewModel.updateFilter(activeFilter.value.copy(excludedAdditives = it))
        },
        FilterConfig("Labels", availableLabels, activeFilter.value.allowedLabels) {
            settingsViewModel.updateFilter(activeFilter.value.copy(allowedLabels = it))
        },
        FilterConfig("Nutri-Score", availableNutriScores, activeFilter.value.allowedNutriScore) {
            settingsViewModel.updateFilter(activeFilter.value.copy(allowedNutriScore = it))
        },
        FilterConfig("Available in", availableCountries, activeFilter.value.allowedCountry) {
            settingsViewModel.updateFilter(activeFilter.value.copy(allowedCountry = it))
        },
        FilterConfig("brands", availableBrands, activeFilter.value.excludedBrands) {
            settingsViewModel.updateFilter(activeFilter.value.copy(excludedBrands = it))
        },
        FilterConfig("Konzerne", availableCorporations, activeFilter.value.excludedCorporations) {
            settingsViewModel.updateFilter(activeFilter.value.copy(excludedCorporations = it))
        }
    )

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

data class FilterConfig(
    val title: String,
    val items: List<String>,
    val selectedItems: List<String>,
    val onUpdate: (List<String>) -> Unit
)