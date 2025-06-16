package de.syntax_institut.androidabschlussprojekt.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.syntax_institut.androidabschlussprojekt.ui.screen.BeautyListScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.DetailScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.FoodListScreen
import de.syntax_institut.androidabschlussprojekt.ui.screen.ScanScreen

@Composable
fun AppStart() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { },
        bottomBar = { },
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
