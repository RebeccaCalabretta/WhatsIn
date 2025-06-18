package de.syntax_institut.androidabschlussprojekt.utils

fun getTranslation(translations: Map<String, String>?): String {
    return translations?.get("de") ?: translations?.get("en") ?: "Keine Informationen verfügbar"
}