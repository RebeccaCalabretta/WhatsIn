package de.syntax_institut.androidabschlussprojekt.utils

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.helper.ProductType

fun String.formatTag(): String =
    removePrefix("en:")
        .replace("-", " ")
        .replaceFirstChar { it.uppercaseChar() }

fun String.toProductType(): ProductType {
    Log.d("ProductTypeMapper", "Mapping input: '$this' → ${this.trim().uppercase()}")

    return when (this.trim().uppercase()) {
        "FOOD" -> ProductType.FOOD
        "BEAUTY" -> ProductType.BEAUTY
        else -> {
            Log.w("ProductType", "Unbekannter productType: '$this' – setze auf UNKNOWN")
            ProductType.UNKNOWN
        }
    }
}