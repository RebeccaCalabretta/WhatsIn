package de.syntax_institut.androidabschlussprojekt.data.mapping


object CountryMapper {
    private val countryMap = mapOf(
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

    fun map(tag: String): String? = countryMap[tag]

    fun allGermanValues(): List<String> = countryMap.values.sorted()

    fun reverseMap(label: String): String? = countryMap.entries.find { it.value == label }?.key

}