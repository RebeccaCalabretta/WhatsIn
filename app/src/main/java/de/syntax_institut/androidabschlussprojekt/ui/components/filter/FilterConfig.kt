package de.syntax_institut.androidabschlussprojekt.ui.components.filter

data class FilterConfig(
    val title: String,
    val items: List<String>,
    val selectedItems: List<String>,
    val onUpdate: (List<String>) -> Unit
)