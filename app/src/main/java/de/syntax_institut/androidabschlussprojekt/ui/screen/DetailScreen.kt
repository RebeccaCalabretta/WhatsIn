package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import de.syntax_institut.androidabschlussprojekt.utils.capitalizeWords
import de.syntax_institut.androidabschlussprojekt.utils.formatNutriments
import de.syntax_institut.androidabschlussprojekt.viewModel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    productViewModel: ProductViewModel = koinViewModel(),
    barcode: String
) {
    Log.d("AppFlow", "DetailScreen geladen mit Barcode $barcode")

    LaunchedEffect(barcode) {
        productViewModel.getProductByBarcode(barcode)
    }

    val productState = productViewModel.selectedProduct.collectAsState()
    val product = productState.value

    if (product == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = "Produktbild",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Produktdetails",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Name: ${product.name?.capitalizeWords()}")
            Text("Marke: ${product.brand}")
            Text("Barcode: ${product.barcode}")
            Text("Zutaten: ${product.ingredients}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Nährwerte (pro 100g):",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = product.nutriments.formatNutriments(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}