package de.syntax_institut.androidabschlussprojekt.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import java.util.Locale

@Composable
fun getTranslation(translations: Map<String, String>?): String {
    val currentLanguage = Locale.getDefault().language
    return translations?.get(currentLanguage)
        ?: translations?.get("en")
        ?: stringResource(R.string.no_data)
}