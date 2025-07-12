package de.syntax_institut.androidabschlussprojekt.utils.filter

fun prepareFilterItems(
    rawItems: List<String>,
    selected: List<String>,
    update: (List<String>) -> Unit,
    allLabel: String? = null,
    searchValue: String = "",
    map: (String) -> String = { it }
): Pair<List<String>, (String) -> Unit> {
    val cleanItems = rawItems.filter { it.isNotBlank() }.distinct()

    val sortedItems = buildList {
        if (searchValue.isBlank()) {
            allLabel?.let { add(it) }
        }
        val selectedSorted = selected.intersect(cleanItems).sortedBy { map(it).lowercase() }
        val restSorted = cleanItems.minus(selected).sortedBy { map(it).lowercase() }
        addAll(selectedSorted)
        addAll(restSorted)
    }

    val visibleItems = sortedItems.map { map(it) }

    val onToggle: (String) -> Unit = { clicked ->
        val tag = cleanItems.find { map(it) == clicked } ?: clicked
        val current = selected.toMutableList()

        when {
            clicked == allLabel -> {
                if (allLabel !in current) {
                    current.clear()
                    current.add(allLabel)
                    current.addAll(cleanItems)
                } else {
                    current.clear()
                }
            }
            tag in current -> {
                current.remove(tag)
                allLabel?.let { current.remove(it) }
            }
            else -> {
                current.add(tag)
                allLabel?.let { current.remove(it) }
            }
        }

        update(current)
    }

    return visibleItems to onToggle
}

fun prepareMappedItems(
    raw: List<String>,
    selected: List<String>,
    update: (List<String>) -> Unit,
    mapper: (String) -> String,
    allLabel: String? = null,
    searchValue: String = ""
): Pair<List<String>, (String) -> Unit> {
    return prepareFilterItems(
        rawItems = raw,
        selected = selected,
        update = update,
        allLabel = allLabel,
        searchValue = searchValue,
        map = mapper
    )
}