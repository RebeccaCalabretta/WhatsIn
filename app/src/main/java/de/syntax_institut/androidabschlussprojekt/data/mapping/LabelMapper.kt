package de.syntax_institut.androidabschlussprojekt.data.mapping

object LabelMapper {

    private val labelMapDe = mapOf(
        "en:no-added-sugar" to "Ohne Zuckerzusatz",
        "en:low-or-no-sugar" to "Zuckerreduziert",
        "en:low-fat" to "Fettarm",
        "en:low-or-no-salt" to "Salzarm",
        "en:no-gluten" to "Glutenfrei",
        "en:no-lactose" to "Laktosefrei",
        "en:milk-free" to "Milchfrei",
        "en:soy-free" to "Sojafrei",
        "en:nut-free" to "Nussfrei",
        "en:vegan" to "Vegan",
        "en:vegetarian" to "Vegetarisch",
        "en:organic" to "Bio",
        "en:eu-organic" to "EU-Bio",
        "en:demeter" to "Demeter",
        "en:fair-trade" to "Fairtrade",
        "en:no-palm-oil" to "Ohne Palmöl",
        "de:ohne-gentechnik" to "Ohne Gentechnik",
        "en:no-preservatives" to "Ohne Konservierungsstoffe",
        "en:no-colorings" to "Ohne Farbstoffe",
        "en:no-artificial-flavors" to "Ohne künstliche Aromen",
        "en:no-artificial-sweeteners" to "Ohne künstliche Süßstoffe",
        "en:bpa-free" to "BPA-frei",
        "en:plastic-free" to "Plastikfrei",
        "en:sustainable" to "Umweltfreundlich",
        "en:carbon-neutral" to "Klimaneutral"
    )

    private val labelMapEn = mapOf(
        "en:no-added-sugar" to "No Added Sugar",
        "en:low-or-no-sugar" to "Reduced Sugar",
        "en:low-fat" to "Low Fat",
        "en:low-or-no-salt" to "Low Salt",
        "en:no-gluten" to "Gluten Free",
        "en:no-lactose" to "Lactose Free",
        "en:milk-free" to "Milk Free",
        "en:soy-free" to "Soy Free",
        "en:nut-free" to "Nut Free",
        "en:vegan" to "Vegan",
        "en:vegetarian" to "Vegetarian",
        "en:organic" to "Organic",
        "en:eu-organic" to "EU Organic",
        "en:demeter" to "Demeter",
        "en:fair-trade" to "Fairtrade",
        "en:no-palm-oil" to "No Palm Oil",
        "de:ohne-gentechnik" to "GMO Free",
        "en:no-preservatives" to "No Preservatives",
        "en:no-colorings" to "No Colorings",
        "en:no-artificial-flavors" to "No Artificial Flavors",
        "en:no-artificial-sweeteners" to "No Artificial Sweeteners",
        "en:bpa-free" to "BPA Free",
        "en:plastic-free" to "Plastic Free",
        "en:sustainable" to "Sustainable",
        "en:carbon-neutral" to "Carbon Neutral"
    )

    fun map(tag: String, selectedLanguage: String): String =
        when (selectedLanguage) {
            "de" -> labelMapDe[tag] ?: tag.removePrefix("en:")
            "en" -> labelMapEn[tag] ?: tag.removePrefix("en:")
            else -> labelMapEn[tag] ?: tag.removePrefix("en:")
        }

    fun reverseMap(label: String, selectedLanguage: String): String? =
        when (selectedLanguage) {
            "de" -> labelMapDe.entries.find { it.value == label }?.key
            "en" -> labelMapEn.entries.find { it.value == label }?.key
            else -> labelMapEn.entries.find { it.value == label }?.key
        }
}