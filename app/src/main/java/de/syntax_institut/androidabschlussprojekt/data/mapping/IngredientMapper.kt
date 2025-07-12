package de.syntax_institut.androidabschlussprojekt.data.mapping

object IngredientMapper {

    private val ingredientMapDe = mapOf(
        "en:sugar" to "Zucker",
        "en:palm-oil" to "Palmöl",
        "en:milk" to "Milch",
        "en:egg" to "Ei",
        "en:gluten" to "Gluten",
        "en:wheat" to "Weizen",
        "en:lactose" to "Laktose",
        "en:soya" to "Soja",
        "en:peanuts" to "Erdnüsse",
        "en:hazelnuts" to "Haselnüsse",
        "en:almonds" to "Mandeln",
        "en:cashew-nuts" to "Cashewkerne",
        "en:walnuts" to "Walnüsse",
        "en:pistachio-nuts" to "Pistazien",
        "en:corn" to "Mais",
        "en:gelatin" to "Gelatine",
        "en:artificial-sweetener" to "Künstlicher Süßstoff",
        "en:colour" to "Farbstoff",
        "en:preservative" to "Konservierungsstoff",
        "en:flavour-enhancer" to "Geschmacksverstärker",
        "en:glutamate" to "Glutamat",
        "en:monosodium-glutamate" to "Mononatriumglutamat",
        "en:fructose" to "Fruktose",
        "en:maltodextrin" to "Maltodextrin",
        "en:aspartame" to "Aspartam",
        "en:sucralose" to "Sucralose",
        "en:sorbitol" to "Sorbit",
        "en:xylitol" to "Xylit",
        "en:acesulfame-k" to "Acesulfam K",
        "en:erythritol" to "Erythrit",
        "en:carrageenan" to "Carrageen",
        "en:glucose-syrup" to "Glukosesirup",
        "en:coconut" to "Kokosnuss",
        "en:crustaceans" to "Krustentiere",
        "en:shellfish" to "Schalentiere",
        "en:fish" to "Fisch",
        "en:mustard" to "Senf",
        "en:celery" to "Sellerie",
        "en:sesame-seeds" to "Sesam",
        "en:lupin" to "Lupine",
        "en:sulphur-dioxide" to "Schwefeldioxid",
        "en:caffeine" to "Koffein",
        "en:alcohol" to "Alkohol"
    )

    private val ingredientMapEn = mapOf(
        "en:sugar" to "Sugar",
        "en:palm-oil" to "Palm Oil",
        "en:milk" to "Milk",
        "en:egg" to "Egg",
        "en:gluten" to "Gluten",
        "en:wheat" to "Wheat",
        "en:lactose" to "Lactose",
        "en:soya" to "Soya",
        "en:peanuts" to "Peanuts",
        "en:hazelnuts" to "Hazelnuts",
        "en:almonds" to "Almonds",
        "en:cashew-nuts" to "Cashew Nuts",
        "en:walnuts" to "Walnuts",
        "en:pistachio-nuts" to "Pistachios",
        "en:corn" to "Corn",
        "en:gelatin" to "Gelatin",
        "en:artificial-sweetener" to "Artificial Sweetener",
        "en:colour" to "Colour",
        "en:preservative" to "Preservative",
        "en:flavour-enhancer" to "Flavour Enhancer",
        "en:glutamate" to "Glutamate",
        "en:monosodium-glutamate" to "Monosodium Glutamate",
        "en:fructose" to "Fructose",
        "en:maltodextrin" to "Maltodextrin",
        "en:aspartame" to "Aspartame",
        "en:sucralose" to "Sucralose",
        "en:sorbitol" to "Sorbitol",
        "en:xylitol" to "Xylitol",
        "en:acesulfame-k" to "Acesulfame K",
        "en:erythritol" to "Erythritol",
        "en:carrageenan" to "Carrageenan",
        "en:glucose-syrup" to "Glucose Syrup",
        "en:coconut" to "Coconut",
        "en:crustaceans" to "Crustaceans",
        "en:shellfish" to "Shellfish",
        "en:fish" to "Fish",
        "en:mustard" to "Mustard",
        "en:celery" to "Celery",
        "en:sesame-seeds" to "Sesame Seeds",
        "en:lupin" to "Lupin",
        "en:sulphur-dioxide" to "Sulphur Dioxide",
        "en:caffeine" to "Caffeine",
        "en:alcohol" to "Alcohol"
    )

    fun map(tag: String, selectedLanguage: String): String =
        when (selectedLanguage) {
            "de" -> ingredientMapDe[tag] ?: tag.removePrefix("en:")
            "en" -> ingredientMapEn[tag] ?: tag.removePrefix("en:")
            else -> ingredientMapEn[tag] ?: tag.removePrefix("en:")
        }

    fun getReverseMap(selectedLanguage: String): Map<String, String> = when (selectedLanguage) {
        "de" -> ingredientMapDe.entries.associate { (tag, label) -> label to tag }
        "en" -> ingredientMapEn.entries.associate { (tag, label) -> label to tag }
        else -> ingredientMapEn.entries.associate { (tag, label) -> label to tag }
    }
}