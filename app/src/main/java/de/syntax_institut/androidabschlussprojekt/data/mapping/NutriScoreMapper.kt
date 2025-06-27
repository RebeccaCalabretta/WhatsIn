package de.syntax_institut.androidabschlussprojekt.data.mapping

object NutriScoreMapper {
    val values = listOf("A", "B", "C", "D", "E")

    fun map(tag: String): String = tag.uppercase()
    fun reverseMap(label: String): String = label.uppercase()
    fun allValues(): List<String> = values
}