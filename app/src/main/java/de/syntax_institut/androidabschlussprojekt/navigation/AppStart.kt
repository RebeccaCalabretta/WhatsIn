package de.syntax_institut.androidabschlussprojekt.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.syntax_institut.androidabschlussprojekt.ui.components.general.BottomNavigationBar
import de.syntax_institut.androidabschlussprojekt.ui.components.general.GradientFab
import de.syntax_institut.androidabschlussprojekt.ui.components.general.TopBar
import de.syntax_institut.androidabschlussprojekt.ui.screen.BeautyListScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.DetailScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.FilterScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.FoodListScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.ScanScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.SettingsScreen

@Composable
fun AppStart() {
    val navController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf(TabItem.Scan) }

    val currentEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentEntry?.destination?.route

    val showBottomBar = currentDestination != FilterRoute::class.qualifiedName
    val showFab = selectedTab != TabItem.Scan && showBottomBar

    Scaffold(
        topBar = {
            TopBar(navController)
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    selectedTabItem = selectedTab,
                    onSelectedTabItem = {
                        selectedTab = it
                        navController.navigate(it.route)
                    }
                )
            }
        },
        floatingActionButton = {
            if (showFab) {
                GradientFab(
                    onClick = {
                        selectedTab = TabItem.Scan
                        navController.navigate(ScanRoute)
                    },
                    iconResId = TabItem.Scan.iconResId
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = ScanRoute,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<ScanRoute> {
                    ScanScreen(
                        onNavigateToDetail = { barcode ->
                            navController.navigate(DetailRoute(barcode))
                        }
                    )
                }
                composable<FoodListRoute> {
                    FoodListScreen()
                }
                composable<BeautyListRoute> {
                    BeautyListScreen()
                }
                composable<DetailRoute> {
                    val route = it.toRoute<DetailRoute>()
                    DetailScreen(barcode = route.barcode)
                }
                composable<SettingsRoute> {
                    SettingsScreen(navController = navController)
                }
                composable<FilterRoute> {
                    FilterScreen()
                }
            }
        }
    )
}