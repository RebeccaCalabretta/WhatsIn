package de.syntax_institut.androidabschlussprojekt.utils

fun String.formatTag(): String =
    removePrefix("en:")
        .replace("-", " ")
        .replaceFirstChar { it.uppercaseChar() }