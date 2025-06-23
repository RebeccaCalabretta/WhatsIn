package de.syntax_institut.androidabschlussprojekt.ui.components.general

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import de.syntax_institut.androidabschlussprojekt.navigation.BeautyListRoute
import de.syntax_institut.androidabschlussprojekt.navigation.FilterRoute
import de.syntax_institut.androidabschlussprojekt.navigation.FoodListRoute
import de.syntax_institut.androidabschlussprojekt.navigation.ScanRoute
import de.syntax_institut.androidabschlussprojekt.navigation.SettingsRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val showBackButton = currentDestination != ScanRoute::class.qualifiedName
    val showSettingsIcon =
        currentDestination == ScanRoute::class.qualifiedName ||
                currentDestination == FoodListRoute::class.qualifiedName ||
                currentDestination == BeautyListRoute::class.qualifiedName ||
                currentDestination?.contains("DetailRoute") == true

    val title = when {
        currentDestination == ScanRoute::class.qualifiedName -> "What's In"
        currentDestination == SettingsRoute::class.qualifiedName -> "Settings"
        currentDestination == FilterRoute::class.qualifiedName -> "Filter Options"
        currentDestination == FoodListRoute::class.qualifiedName -> "Food List"
        currentDestination == BeautyListRoute::class.qualifiedName -> "Beauty List"
        currentDestination?.contains("DetailRoute") == true -> "Product Details"
        else -> ""
    }

    CenterAlignedTopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Zurück"
                    )
                }
            }
        },
        actions = {
            if (showSettingsIcon) {
                IconButton(onClick = {
                    navController.navigate(SettingsRoute)
                }) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = "Einstellungen")
                }
            }
        }
    )
}