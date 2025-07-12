package de.syntax_institut.androidabschlussprojekt.data.mapping

object NutriScoreMapper {
    val values = listOf("a", "b", "c", "d", "e")

    fun map(tag: String): String = tag.trim().uppercase()

    fun getReverseMap(): Map<String, String> =
        values.associate { it.uppercase() to it }
}