package de.syntax_institut.androidabschlussprojekt.utils.filter

data class FilterConfig(
    val title: String,
    val items: List<String>,
    val selectedItems: List<String>,
    val onToggleItem: (String) -> Unit
)