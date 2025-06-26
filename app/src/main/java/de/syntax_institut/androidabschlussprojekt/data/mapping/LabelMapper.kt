package de.syntax_institut.androidabschlussprojekt.data.mapping

object LabelMapper {
    private val labelMap = mapOf(
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

    fun map(tag: String): String? = labelMap[tag]
}