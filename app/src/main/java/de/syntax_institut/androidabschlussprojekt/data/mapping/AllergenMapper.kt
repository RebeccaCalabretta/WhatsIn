package de.syntax_institut.androidabschlussprojekt.data.mapping

import android.util.Log

object AllergenMapper {

    private val allergenMapDe = mapOf(
        "en:milk" to "Milch",
        "en:cream" to "Sahne",
        "en:butter" to "Butter",
        "en:whey" to "Molke",
        "en:cheese" to "Käse",
        "en:lactose" to "Laktose",

        "en:egg" to "Ei",
        "en:egg-white" to "Eiklar",
        "en:egg-yolk" to "Eigelb",

        "en:gluten" to "Gluten",
        "en:wheat" to "Weizen",
        "en:spelt" to "Dinkel",
        "en:oats" to "Hafer",
        "en:rye" to "Roggen",
        "en:barley" to "Gerste",
        "en:cereal" to "Getreide",
        "en:flour" to "Mehl",

        "en:nuts" to "Schalenfrüchte",
        "en:almonds" to "Mandeln",
        "en:hazelnuts" to "Haselnüsse",
        "en:walnuts" to "Walnüsse",
        "en:cashew-nuts" to "Cashewkerne",
        "en:pecan-nuts" to "Pekannüsse",
        "en:macadamia-nuts" to "Macadamianüsse",
        "en:pistachio-nuts" to "Pistazien",
        "en:tree-nut" to "Baumnuss",

        "en:soybeans" to "Sojabohnen",
        "en:peanuts" to "Erdnüsse",
        "en:lupin" to "Lupine",
        "en:legume" to "Hülsenfrucht",

        "en:fish" to "Fisch",
        "en:crustaceans" to "Krustentiere",
        "en:molluscs" to "Weichtiere",
        "en:gelatin" to "Gelatine",
        "en:beef" to "Rindfleisch",
        "en:pork" to "Schwein",
        "en:chicken" to "Huhn",

        "en:mustard" to "Senf",
        "en:celery" to "Sellerie",
        "en:sesame-seeds" to "Sesam",
        "en:garlic" to "Knoblauch",
        "en:onion" to "Zwiebel",

        "en:apple" to "Apfel",
        "en:banana" to "Banane",
        "en:kiwi" to "Kiwi",
        "en:orange" to "Orange",
        "en:lemon" to "Zitrone",
        "en:peach" to "Pfirsich",

        "en:sulphur-dioxide-and-sulphites" to "Schwefeldioxid & Sulfite",
        "en:metabisulphite" to "Metabisulfit",
        "en:phenylalanine" to "Phenylalanin",
        "en:preservative" to "Konservierungsstoff",
        "en:colour" to "Farbstoff",
        "en:flavouring" to "Aroma",

        "en:soy" to "Soja",
        "en:soya" to "Soja",
        "en:soya-lecithin" to "Sojalecithin",
        "en:lactic-ferments" to "Milchsäurebakterien",
        "en:yeast" to "Hefe",
        "en:yeast-extract" to "Hefeextrakt",

        "en:food-additives" to "Zusatzstoffe",
        "en:nut" to "Nuss",
        "en:oatmeal" to "Haferflocken",
        "en:fruit" to "Obst",
        "en:vegetable" to "Gemüse"
    )

    private val allergenMapEn = mapOf(
        "en:milk" to "Milk",
        "en:cream" to "Cream",
        "en:butter" to "Butter",
        "en:whey" to "Whey",
        "en:cheese" to "Cheese",
        "en:lactose" to "Lactose",

        "en:egg" to "Egg",
        "en:egg-white" to "Egg White",
        "en:egg-yolk" to "Egg Yolk",

        "en:gluten" to "Gluten",
        "en:wheat" to "Wheat",
        "en:spelt" to "Spelt",
        "en:oats" to "Oats",
        "en:rye" to "Rye",
        "en:barley" to "Barley",
        "en:cereal" to "Cereal",
        "en:flour" to "Flour",

        "en:nuts" to "Tree Nuts",
        "en:almonds" to "Almonds",
        "en:hazelnuts" to "Hazelnuts",
        "en:walnuts" to "Walnuts",
        "en:cashew-nuts" to "Cashew Nuts",
        "en:pecan-nuts" to "Pecan Nuts",
        "en:macadamia-nuts" to "Macadamia Nuts",
        "en:pistachio-nuts" to "Pistachios",
        "en:tree-nut" to "Tree Nut",

        "en:soybeans" to "Soybeans",
        "en:peanuts" to "Peanuts",
        "en:lupin" to "Lupin",
        "en:legume" to "Legume",

        "en:fish" to "Fish",
        "en:crustaceans" to "Crustaceans",
        "en:molluscs" to "Molluscs",
        "en:gelatin" to "Gelatin",
        "en:beef" to "Beef",
        "en:pork" to "Pork",
        "en:chicken" to "Chicken",

        "en:mustard" to "Mustard",
        "en:celery" to "Celery",
        "en:sesame-seeds" to "Sesame Seeds",
        "en:garlic" to "Garlic",
        "en:onion" to "Onion",

        "en:apple" to "Apple",
        "en:banana" to "Banana",
        "en:kiwi" to "Kiwi",
        "en:orange" to "Orange",
        "en:lemon" to "Lemon",
        "en:peach" to "Peach",

        "en:sulphur-dioxide-and-sulphites" to "Sulphur Dioxide & Sulphites",
        "en:metabisulphite" to "Metabisulphite",
        "en:phenylalanine" to "Phenylalanine",
        "en:preservative" to "Preservative",
        "en:colour" to "Color",
        "en:flavouring" to "Flavouring",

        "en:soy" to "Soy",
        "en:soya" to "Soy",
        "en:soya-lecithin" to "Soy Lecithin",
        "en:lactic-ferments" to "Lactic Ferments",
        "en:yeast" to "Yeast",
        "en:yeast-extract" to "Yeast Extract",

        "en:food-additives" to "Additives",
        "en:nut" to "Nut",
        "en:oatmeal" to "Oatmeal",
        "en:fruit" to "Fruit",
        "en:vegetable" to "Vegetable"
    )

    fun map(tag: String, lang: String): String {
        Log.d("AllergenMapper", "called with tag=$tag, lang=$lang")
        return when (lang) {
            "de" -> allergenMapDe[tag] ?: tag.removePrefix("en:")
            "en" -> allergenMapEn[tag] ?: tag.removePrefix("en:")
            else -> allergenMapEn[tag] ?: tag.removePrefix("en:")
        }
    }

    fun getReverseMap(lang: String): Map<String, String> = when (lang) {
        "de" -> allergenMapDe.entries.associate { (tag, label) -> label to tag }
        "en" -> allergenMapEn.entries.associate { (tag, label) -> label to tag }
        else -> allergenMapEn.entries.associate { (tag, label) -> label to tag }
    }
}