package de.syntax_institut.androidabschlussprojekt.utils.filter


fun List<String>.cleanAndDistinct(): List<String> =
    this.filter { it.isNotBlank() && it.firstOrNull()?.isUpperCase() == true }
        .distinct()

fun prepareItemsWithAll(
    rawItems: List<String>,
    selected: List<String>,
    allLabel: String,
    update: (List<String>) -> Unit
): Pair<List<String>, (String) -> Unit> {
    val cleaned = rawItems.cleanAndDistinct()
    val (selectedItems, unselectedItems) = cleaned.partition { it in selected }

    val sorted = buildList {
        add(allLabel)
        addAll(selectedItems.sorted())
        addAll(unselectedItems.sorted())
    }

    val toggle: (String) -> Unit = { item ->
        val current = selected.toMutableList()
        when {
            item == allLabel && allLabel !in current -> {
                current.clear()
                current.add(allLabel)
                current.addAll(cleaned)
            }

            item == allLabel -> current.clear()
            item in current -> {
                current.remove(item)
                current.remove(allLabel)
            }

            else -> {
                current.add(item)
                current.remove(allLabel)
            }
        }
        update(current)
    }

    return sorted to toggle
}

fun prepareGenericItems(
    rawItems: List<String>,
    selected: List<String>,
    update: (List<String>) -> Unit
): Pair<List<String>, (String) -> Unit> {
    val cleaned = rawItems.cleanAndDistinct()
    val (selectedItems, unselectedItems) = cleaned.partition { it in selected }

    val sorted = selectedItems.sorted() + unselectedItems.sorted()

    val toggle: (String) -> Unit = { item ->
        val updated = if (item in selected) selected - item else selected + item
        update(updated)
    }

    return sorted to toggle
}