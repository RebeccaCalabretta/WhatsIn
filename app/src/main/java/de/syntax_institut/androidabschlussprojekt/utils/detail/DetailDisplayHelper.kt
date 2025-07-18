package de.syntax_institut.androidabschlussprojekt.utils.detail

import android.content.Context
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.NutriScoreMapper

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

    fun getDisplayNutriScore(
        nutritionGrades: String?,
        labelsTags: List<String>?,
        context: Context
    ): String {
        val grade = nutritionGrades?.trim()?.lowercase()
        if (grade in NutriScoreMapper.values) if (grade != null) {
            return grade.uppercase()
        }

        val fallbackGrade = labelsTags
            ?.firstOrNull { it.startsWith("en:nutriscore-grade-") }
            ?.substringAfterLast("-")
            ?.lowercase()
        if (fallbackGrade in NutriScoreMapper.values) if (fallbackGrade != null) {
            return fallbackGrade.uppercase()
        }

        return context.getString(R.string.no_data)
    }
}