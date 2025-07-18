package de.syntax_institut.androidabschlussprojekt.model

import de.syntax_institut.androidabschlussprojekt.helper.FilterType

data class FilterViolation(
    val resId: Int,
    val value: String? = null,
    val type: FilterType
)
