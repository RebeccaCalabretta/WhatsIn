package de.syntax_institut.androidabschlussprojekt.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.syntax_institut.androidabschlussprojekt.ui.components.general.BottomNavigationBar
import de.syntax_institut.androidabschlussprojekt.ui.screen.BeautyListScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.DetailScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.FoodListScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.ScanScreen

@Composable
fun AppStart() {
    Log.d("AppFlow", "AppStart geladen")

    val navController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf(TabItem.Scan) }


    Scaffold(
        topBar = { },
        bottomBar = {
            BottomNavigationBar(
                selectedTabItem = selectedTab,
                onSelectedTabItem = {
                    selectedTab = it
                    navController.navigate(it.route)
                }
            )
        },
        floatingActionButton = { }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScanRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<ScanRoute>{
                ScanScreen(
                    onNavigateToDetail = { barcode ->
                        navController.navigate(DetailRoute(barcode))
                    }
                )
            }
            composable<FoodListRoute>{
                FoodListScreen()
            }
            composable<BeautyListRoute>{
                BeautyListScreen()
            }
            composable<DetailRoute> {
                val route = it.toRoute<DetailRoute>()
                DetailScreen(barcode = route.barcode)
            }
        }
    }
}
