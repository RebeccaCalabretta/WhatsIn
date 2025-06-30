package de.syntax_institut.androidabschlussprojekt.utils

import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.helper.ProductType

fun String.formatTag(): String =
    removePrefix("en:")
        .replace("-", " ")
        .replaceFirstChar { it.uppercaseChar() }

fun String.toProductType(): ProductType = when (this.lowercase()) {
    "food" -> ProductType.FOOD
    "beauty" -> ProductType.BEAUTY
    else -> throw ProductException(ProductError.NOT_FOUND)
}