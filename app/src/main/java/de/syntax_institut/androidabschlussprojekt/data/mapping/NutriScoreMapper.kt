package de.syntax_institut.androidabschlussprojekt.data.mapping

object NutriScoreMapper {
    val values = listOf("a", "b", "c", "d", "e")

    fun map(tag: String?): String? {
        val normalized = tag?.trim()?.lowercase()
        return if (normalized in values) normalized else null
    }

    fun getReverseMap(): Map<String, String> =
        values.associate { it.uppercase() to it }
}