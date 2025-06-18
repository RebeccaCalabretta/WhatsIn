package de.syntax_institut.androidabschlussprojekt.utils

fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }