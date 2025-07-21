package de.syntax_institut.androidabschlussprojekt.utils

import de.syntax_institut.androidabschlussprojekt.helper.ProductType

fun String.toProductType(): ProductType {

    return when (this.trim().uppercase()) {
        "FOOD" -> ProductType.FOOD
        "BEAUTY" -> ProductType.BEAUTY
        else -> {
            ProductType.UNKNOWN
        }
    }
}