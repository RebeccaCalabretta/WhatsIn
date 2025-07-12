package de.syntax_institut.androidabschlussprojekt.utils.filter

data class FilterConfig(
    val titleRes: Int,
    val items: List<String>,
    val selectedItems: List<String>,
    val onToggleItem: (String) -> Unit,
    val labelMapper: (String) -> String = { it }
)