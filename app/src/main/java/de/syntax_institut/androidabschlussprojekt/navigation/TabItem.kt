package de.syntax_institut.androidabschlussprojekt.navigation

import androidx.annotation.DrawableRes
import de.syntax_institut.androidabschlussprojekt.R

enum class TabItem(
    val route: Any,
    val label: String,
    @DrawableRes val iconResId: Int
) {
    Scan(ScanRoute, "Scan", R.drawable.ic_crop_free),
    Food(FoodListRoute, "Food", R.drawable.ic_fastfood),
    Beauty(BeautyListRoute, "Beauty", R.drawable.ic_face),
    Settings(SettingsRoute, "Settings", R.drawable.ic_settings)
}