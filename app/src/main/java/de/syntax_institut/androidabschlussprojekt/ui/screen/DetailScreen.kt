package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.syntax_institut.androidabschlussprojekt.ui.components.detail.ProductDetailContent
import de.syntax_institut.androidabschlussprojekt.ui.components.detail.ProductHeaderSection
import de.syntax_institut.androidabschlussprojekt.ui.components.detail.ProductInfoSection
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    productViewModel: ProductViewModel = koinViewModel(),
    filterViewModel: FilterViewModel = koinViewModel(),
    barcode: String
) {
    Log.d("AppFlow", "DetailScreen geladen mit Barcode $barcode")

    val productState = productViewModel.selectedProduct.collectAsState()
    val product = productState.value
    val filterViolations by filterViewModel.filterViolations.collectAsState()
    var showFilterDialog by remember { mutableStateOf(false) }

    LaunchedEffect(barcode) {
        productViewModel.loadProductFromDatabase(barcode)
    }

    LaunchedEffect(product) {
        product?.let {
            Log.d("FilterCheck", "Produkt Tags: ${it.countriesTags}")
            Log.d("FilterCheck", "Erlaubte Länder: ${filterViewModel.activeFilter.value.allowedCountry}")

            filterViewModel.validateProduct(it)
            showFilterDialog = filterViewModel.filterViolations.value.isNotEmpty()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            productViewModel.clearSelectedProduct()
            Log.d("DetailScreen", "selectedProduct wurde gelöscht")
        }
    }

    if (product == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = { showFilterDialog = false },
                confirmButton = {
                    TextButton(onClick = { showFilterDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Achtung") },
                text = { Text("Dieses Produkt entspricht nicht deinen Filterkriterien.") }
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            ProductHeaderSection(
               imageUrl =  product.imageUrl,
                filterViolations = filterViolations,
                isFavorite = product.isFavorite,
               onToggleFavorite =  { productViewModel.toggleFavorite() }
            )
            ProductInfoSection(
                name = product.name,
                brand = product.brand,
                corporation = product.corporation,
                labels = product.labelsTags
            )
            ProductDetailContent(
                ingredientsText = product.ingredientsText,
                nutriments = product.nutriments,
                additivesTags = product.additivesTags,
                allergensTags = product.allergensTags,
                nutriScore = product.nutriScore,
                corporation = product.corporation
            )
        }
    }
}