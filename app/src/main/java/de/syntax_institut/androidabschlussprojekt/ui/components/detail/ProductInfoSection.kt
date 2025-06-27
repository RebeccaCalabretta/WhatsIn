package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductInfoSection(
    name: String?,
    brand: String?,
    corporation: String?,
    labels: List<String>
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(name ?: "Unbekannt", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(2.dp))
        Text(brand ?: "Keine Marke", style = MaterialTheme.typography.bodyMedium)
        if (!corporation.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text("Konzern: $corporation", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        ProductLabelSection(labels)
    }
}