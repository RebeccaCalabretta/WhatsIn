package de.syntax_institut.androidabschlussprojekt.error

class ProductException(val error: ProductError) : Exception()

enum class ProductError(val messag: String) {
    NETWORK("Kein Netz - bitte überprüfe Deine InternetVerbindung"),
    NOT_FOUND("Produkt nicht gefunden"),
    UNKNOWN("Ein unbekannter Fehler ist aufgetreten")
}