package de.syntax_institut.androidabschlussprojekt.utils.detail

import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper

object DetailDisplayHelper {
    fun getDisplayLabels(
        labelsTags: List<String>?,
        language: String
    ): List<String> {
        return labelsTags
            ?.mapNotNull { LabelMapper.map(it, language) }
            ?.distinct()
            ?: emptyList()
    }
}