package de.syntax_institut.androidabschlussprojekt.data.mapping


object AllergenMapper {

    private val allergenMap = mapOf(

        // 🥛 Milchprodukte
        "en:milk" to "Milch",
        "en:cream" to "Sahne",
        "en:butter" to "Butter",
        "en:whey" to "Molke",
        "en:cheese" to "Käse",
        "en:lactose" to "Laktose",

        // 🥚 Ei
        "en:egg" to "Ei",
        "en:egg-white" to "Eiklar",
        "en:egg-yolk" to "Eigelb",

        // 🌾 Gluten / Getreide
        "en:gluten" to "Gluten",
        "en:wheat" to "Weizen",
        "en:spelt" to "Dinkel",
        "en:oats" to "Hafer",
        "en:rye" to "Roggen",
        "en:barley" to "Gerste",
        "en:cereal" to "Getreide",
        "en:flour" to "Mehl",

        // 🌰 Nüsse / Kerne
        "en:nuts" to "Schalenfrüchte",
        "en:almonds" to "Mandeln",
        "en:hazelnuts" to "Haselnüsse",
        "en:walnuts" to "Walnüsse",
        "en:cashew-nuts" to "Cashewkerne",
        "en:pecan-nuts" to "Pekannüsse",
        "en:macadamia-nuts" to "Macadamianüsse",
        "en:pistachio-nuts" to "Pistazien",
        "en:tree-nut" to "Baumnuss",

        // 🫘 Hülsenfrüchte
        "en:soybeans" to "Sojabohnen",
        "en:peanuts" to "Erdnüsse",
        "en:lupin" to "Lupine",
        "en:legume" to "Hülsenfrucht",

        // 🐟 Tierische Quellen
        "en:fish" to "Fisch",
        "en:crustaceans" to "Krustentiere",
        "en:molluscs" to "Weichtiere",
        "en:gelatin" to "Gelatine",
        "en:beef" to "Rindfleisch",
        "en:pork" to "Schwein",
        "en:chicken" to "Huhn",

        // 🌿 Pflanzen / Samen / Gewürze
        "en:mustard" to "Senf",
        "en:celery" to "Sellerie",
        "en:sesame-seeds" to "Sesam",
        "en:garlic" to "Knoblauch",
        "en:onion" to "Zwiebel",

        // 🍏 Früchte
        "en:apple" to "Apfel",
        "en:banana" to "Banane",
        "en:kiwi" to "Kiwi",
        "en:orange" to "Orange",
        "en:lemon" to "Zitrone",
        "en:peach" to "Pfirsich",

        // 💨 Zusatzstoffe
        "en:sulphur-dioxide-and-sulphites" to "Schwefeldioxid & Sulfite",
        "en:metabisulphite" to "Metabisulfit",
        "en:phenylalanine" to "Phenylalanin",
        "en:preservative" to "Konservierungsstoff",
        "en:colour" to "Farbstoff",
        "en:flavouring" to "Aroma",

        // 🔁 Duplikate & Varianten
        "en:soy" to "Soja",
        "en:soya" to "Soja",
        "en:soya-lecithin" to "Sojalecithin",
        "en:lactic-ferments" to "Milchsäurebakterien",
        "en:yeast" to "Hefe",
        "en:yeast-extract" to "Hefeextrakt",

        // ✅ Weitere Beispiele aus OFFacts
        "en:food-additives" to "Zusatzstoffe",
        "en:nut" to "Nuss",
        "en:oatmeal" to "Haferflocken",
        "en:fruit" to "Obst",
        "en:vegetable" to "Gemüse"
    )

    fun map(tag: String): String = allergenMap[tag] ?: tag.removePrefix("en:")
    fun allGermanValues(): List<String> = allergenMap.values.sorted()
}