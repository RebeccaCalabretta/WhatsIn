package de.syntax_institut.androidabschlussprojekt.navigation

import androidx.annotation.DrawableRes
import de.syntax_institut.androidabschlussprojekt.R

enum class TabItem(
    val route: Any,
    val label: String,
    @DrawableRes val iconResId: Int
) {
    Food(FoodRoute, "Food", R.drawable.ic_fastfood),
    Scan(ScanRoute, "Scan", R.drawable.ic_crop_free),
    Beauty(BeautyRoute, "Beauty", R.drawable.ic_face)
}