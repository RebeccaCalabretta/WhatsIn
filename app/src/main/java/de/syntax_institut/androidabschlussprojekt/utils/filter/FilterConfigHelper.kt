package de.syntax_institut.androidabschlussprojekt.utils.filter

fun prepareFilterItems(
    rawItems: List<String>,
    selected: List<String>,
    update: (List<String>) -> Unit,
    allLabel: String? = null,
    map: (String) -> String = { it }
): Pair<List<String>, (String) -> Unit> {

    val cleanItems = rawItems.filter { it.isNotBlank() }.distinct()

    val sortedItems = buildList {
        allLabel?.let { add(it) }
        addAll(selected.intersect(cleanItems).sorted())
        addAll(cleanItems.minus(selected).sorted())
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

