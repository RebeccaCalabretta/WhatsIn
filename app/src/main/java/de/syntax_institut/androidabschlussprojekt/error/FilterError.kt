package de.syntax_institut.androidabschlussprojekt.error

import de.syntax_institut.androidabschlussprojekt.R

class FilterException(val error: FilterError) : Exception()

enum class FilterError(val messageRes: Int) {
    LOAD_FAILED(R.string.error_filter_load),
    SAVE_FAILED(R.string.error_filter_save),
    UNKNOWN(R.string.error_unknown)
}

