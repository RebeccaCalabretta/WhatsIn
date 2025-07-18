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
    rawItems: List<String>,
    selectedTags: List<String>,
    searchValue: String,
    mapper: (String) -> String,
    reverseMapper: (String) -> String?,
    onUpdate: (List<String>) -> Unit,
    sortSelectedFirst: Boolean = true
): Pair<List<String>, (String) -> Unit> {

    val itemsMapped = rawItems.map { tag -> mapper(tag) to tag }

    val filteredItems = itemsMapped
        .filter { (label, _) -> label.lowercase().contains(searchValue) }

    val sortedItems = if (sortSelectedFirst) {
        filteredItems.sortedWith(
            compareByDescending<Pair<String, String>> { it.second in selectedTags }
                .thenBy { it.first }
        )
    } else {
        filteredItems
    }

    val visibleItems = sortedItems.map { it.first }

    val toggle: (String) -> Unit = { clickedLabel ->
        val clickedTag = reverseMapper(clickedLabel)
        if (clickedTag != null) {
            val updated = if (clickedTag in selectedTags) {
                selectedTags - clickedTag
            } else {
                selectedTags + clickedTag
            }
            onUpdate(updated)
        }
    }

    return Pair(visibleItems, toggle)
}