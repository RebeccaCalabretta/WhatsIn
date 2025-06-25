package de.syntax_institut.androidabschlussprojekt.utils.filters

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterItem
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.helper.FilterType


suspend fun loadAvailableFiltersFromApi(
    repository: FilterRepository
): Map<FilterType, List<FilterItem>> {
    return try {
        val allergens = repository.fetchAllergens()
        Log.d("FilterApiLoader", "fetchAllergens() erfolgreich")

        val additives = repository.fetchAdditives()
        Log.d("FilterApiLoader", "fetchAdditives() erfolgreich")

        val countries = repository.fetchCountries()
        Log.d("FilterApiLoader", "fetchCountries() erfolgreich")

        val brands = repository.fetchBrands()
        Log.d("FilterApiLoader", "fetchBrands() erfolgreich")

        mapOf(
            FilterType.ALLERGENS to allergens,
            FilterType.ADDITIVES to additives,
            FilterType.COUNTRIES to countries,
            FilterType.BRANDS to brands
        )
    } catch (e: Exception) {
        Log.e("FilterApiLoader", "Fehler beim Laden der Filterwerte", e)
        emptyMap()
    }
}
