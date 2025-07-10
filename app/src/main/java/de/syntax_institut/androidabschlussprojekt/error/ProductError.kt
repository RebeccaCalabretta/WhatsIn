package de.syntax_institut.androidabschlussprojekt.error

import de.syntax_institut.androidabschlussprojekt.R

class ProductException(val error: ProductError) : Exception()

enum class ProductError(val messageRes: Int) {
    NETWORK(R.string.error_network),
    NOT_FOUND(R.string.error_not_found),
    UNKNOWN(R.string.error_unknown),
    DATABASE(R.string.error_database)
}