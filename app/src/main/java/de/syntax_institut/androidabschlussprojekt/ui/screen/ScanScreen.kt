package de.syntax_institut.androidabschlussprojekt.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import de.syntax_institut.androidabschlussprojekt.data.dummyProduct
import de.syntax_institut.androidabschlussprojekt.ui.components.ScanPreview
import de.syntax_institut.androidabschlussprojekt.viewModel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScanScreen(
    productViewModel: ProductViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    Log.d("ScreenSpy", "ScanScreen geladen")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Scanner Vorschau")

        Spacer(Modifier.height(40.dp))

        ScanPreview(
            productViewModel = productViewModel,
            onNavigateToDetail = onNavigateToDetail,
            modifier = Modifier
                .size(300.dp)
                .clipToBounds()
        )
        Spacer(Modifier.height(40.dp))

        Button(onClick = {
            onNavigateToDetail(dummyProduct.barcode)
        }) {
            Text("Scannen")
        }
    }
}