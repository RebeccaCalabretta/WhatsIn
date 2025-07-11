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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.ui.components.detail.ProductDetailContent
import de.syntax_institut.androidabschlussprojekt.ui.components.detail.ProductHeaderSection
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.SettingsViewModel

import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    productViewModel: ProductViewModel = koinViewModel(),
    filterViewModel: FilterViewModel = koinViewModel(),
    settingsViewModel: SettingsViewModel = koinViewModel(),
    barcode: String,
    fromScan: Boolean
) {
    Log.d("AppFlow", "DetailScreen geladen mit Barcode $barcode")

    val productState = productViewModel.selectedProduct.collectAsState()
    val product = productState.value
    val filterViolations by filterViewModel.filterViolations.collectAsState()
    val selectedLanguage by settingsViewModel.selectedLanguage.collectAsState()

    var showFilterDialog by remember { mutableStateOf(false) }
    var hasShownDialog by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(barcode) {
        productViewModel.loadProductFromDatabase(barcode)
    }

    LaunchedEffect(product, selectedLanguage) {
        product?.let {
            filterViewModel.validateProduct(it, selectedLanguage)
            if (fromScan && filterViewModel.filterViolations.value.isNotEmpty() && !hasShownDialog) {
                showFilterDialog = true
                hasShownDialog = true
            }
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
                title = { Text(stringResource(R.string.attention))
                },
                text = { Text(stringResource(R.string.product_not_matching_filter))
                }
            )
        }

        val mappedLabels = product.labelsTags.mapNotNull { LabelMapper.map(it, selectedLanguage) }

        Column(modifier = Modifier.fillMaxSize()) {
            ProductHeaderSection(
                imageUrl = product.imageUrl,
                name = product.name,
                brand = product.brand,
                corporation = product.corporation,
                filterViolations = filterViolations,
                isFavorite = product.isFavorite,
                onToggleFavorite = { productViewModel.toggleFavorite() },
                labels = mappedLabels,
                selectedLanguage = selectedLanguage
            )

            ProductDetailContent(
                ingredientsText = product.ingredientsText,
                nutriments = product.nutriments,
                additivesTags = product.additivesTags,
                allergensTags = product.allergensTags,
                nutriScore = product.nutriScore,
                selectedLanguage = selectedLanguage
            )
        }
    }
}