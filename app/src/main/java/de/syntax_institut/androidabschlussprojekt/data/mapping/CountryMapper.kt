package de.syntax_institut.androidabschlussprojekt.data.mapping

object CountryMapper {
    private val countryMapDe = mapOf(
        "en:france" to "Frankreich",
        "en:united-states" to "USA",
        "en:spain" to "Spanien",
        "en:germany" to "Deutschland",
        "en:italy" to "Italien",
        "en:united-kingdom" to "Vereinigtes Königreich",
        "en:canada" to "Kanada",
        "en:switzerland" to "Schweiz",
        "en:belgium" to "Belgien",
        "en:ireland" to "Irland",
        "en:netherlands" to "Niederlande",
        "en:poland" to "Polen",
        "en:norway" to "Norwegen",
        "en:romania" to "Rumänien",
        "en:sweden" to "Schweden",
        "en:austria" to "Österreich",
        "en:finland" to "Finnland",
        "en:czech-republic" to "Tschechien",
        "en:portugal" to "Portugal",
        "en:denmark" to "Dänemark",
        "en:bulgaria" to "Bulgarien",
        "en:luxembourg" to "Luxemburg",
        "en:hungary" to "Ungarn",
        "en:greece" to "Griechenland",
        "en:lithuania" to "Litauen",
        "en:croatia" to "Kroatien",
        "en:slovakia" to "Slowakei",
        "en:ukraine" to "Ukraine",
        "en:slovenia" to "Slowenien",
        "en:latvia" to "Lettland",
        "en:iceland" to "Island",
        "en:cyprus" to "Zypern",
        "en:malta" to "Malta",
        "en:moldova" to "Moldawien",
        "en:estonia" to "Estland",
        "en:serbia" to "Serbien",
        "en:bosnia-and-herzegovina" to "Bosnien und Herzegowina",
        "en:andorra" to "Andorra",
        "en:north-macedonia" to "Nordmazedonien",
        "en:turkey" to "Türkei",
        "en:mexico" to "Mexiko"
    )

    private val countryMapEn = mapOf(
        "en:france" to "France",
        "en:united-states" to "USA",
        "en:spain" to "Spain",
        "en:germany" to "Germany",
        "en:italy" to "Italy",
        "en:united-kingdom" to "United Kingdom",
        "en:canada" to "Canada",
        "en:switzerland" to "Switzerland",
        "en:belgium" to "Belgium",
        "en:ireland" to "Ireland",
        "en:netherlands" to "Netherlands",
        "en:poland" to "Poland",
        "en:norway" to "Norway",
        "en:romania" to "Romania",
        "en:sweden" to "Sweden",
        "en:austria" to "Austria",
        "en:finland" to "Finland",
        "en:czech-republic" to "Czech Republic",
        "en:portugal" to "Portugal",
        "en:denmark" to "Denmark",
        "en:bulgaria" to "Bulgaria",
        "en:luxembourg" to "Luxembourg",
        "en:hungary" to "Hungary",
        "en:greece" to "Greece",
        "en:lithuania" to "Lithuania",
        "en:croatia" to "Croatia",
        "en:slovakia" to "Slovakia",
        "en:ukraine" to "Ukraine",
        "en:slovenia" to "Slovenia",
        "en:latvia" to "Latvia",
        "en:iceland" to "Iceland",
        "en:cyprus" to "Cyprus",
        "en:malta" to "Malta",
        "en:moldova" to "Moldova",
        "en:estonia" to "Estonia",
        "en:serbia" to "Serbia",
        "en:bosnia-and-herzegovina" to "Bosnia and Herzegovina",
        "en:andorra" to "Andorra",
        "en:north-macedonia" to "North Macedonia",
        "en:turkey" to "Turkey",
        "en:mexico" to "Mexico"
    )

    fun map(tag: String, selectedLanguage: String): String =
        when (selectedLanguage) {
            "de" -> countryMapDe[tag] ?: tag.removePrefix("en:")
            "en" -> countryMapEn[tag] ?: tag.removePrefix("en:")
            else -> countryMapEn[tag] ?: tag.removePrefix("en:")
        }

    fun reverseMap(label: String, selectedLanguage: String): String? =
        when (selectedLanguage) {
            "de" -> countryMapDe.entries.find { it.value == label }?.key
            "en" -> countryMapEn.entries.find { it.value == label }?.key
            else -> countryMapEn.entries.find { it.value == label }?.key
        }
}