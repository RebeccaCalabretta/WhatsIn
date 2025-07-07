package de.syntax_institut.androidabschlussprojekt.utils

import de.syntax_institut.androidabschlussprojekt.helper.SortOption

fun SortOption.toUiLabel(): String = when (this) {
    SortOption.NAME_ASC -> "Name A–Z"
    SortOption.NAME_DESC -> "Name Z–A"
    SortOption.DATE_NEWEST_FIRST -> "Neueste zuerst"
    SortOption.DATE_OLDEST_FIRST -> "Älteste zuerst"
}