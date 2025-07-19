package de.syntax_institut.androidabschlussprojekt.utils.filter

fun prepareFilterItems(
    rawItems: List<String>,
    selected: List<String>,
    update: (List<String>) -> Unit,
    allLabel: String? = null,
    searchValue: String = "",
    map: (String) -> String = { it }
): Pair<List<String>, (String) -> Unit> {
    val cleanItems = rawItems.filter { it.isNotBlank() }
    val filteredItems = if (searchValue.isNotBlank()) {
        cleanItems.filter { map(it).contains(searchValue, ignoreCase = true) }
    } else cleanItems

    val chips = if (allLabel != null)
        listOf(allLabel) + filteredItems.sortedBy { map(it).lowercase() }
    else
        filteredItems.sortedBy { map(it).lowercase() }

    val visibleItems = chips.map { map(it) }
    val filteredSelected = selected.filter { it != allLabel }

    val onToggle: (String) -> Unit = { clicked ->
        val tag = cleanItems.find { map(it) == clicked } ?: clicked
        val allVisibleSelected = filteredItems.all { it in filteredSelected }
        val current = when {
            clicked == allLabel && allVisibleSelected -> emptyList()
            clicked == allLabel -> filteredItems
            tag in filteredSelected -> filteredSelected - tag
            else -> filteredSelected + tag
        }
        val finalSelection = if (allLabel != null && current.containsAll(filteredItems) && filteredItems.isNotEmpty())
            listOf(allLabel) + current
        else
            current
        update(finalSelection)
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