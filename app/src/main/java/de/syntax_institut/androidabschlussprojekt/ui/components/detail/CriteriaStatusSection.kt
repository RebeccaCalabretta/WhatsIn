package de.syntax_institut.androidabschlussprojekt.ui.components.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CriteriaStatusSection(filterViolations: List<String>) {
    if (filterViolations.isEmpty()) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Kriterien erfüllt",
                tint = Color(0xFF4CAF50)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Kriterien erfüllt", style = MaterialTheme.typography.bodyMedium)
        }
    } else {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Kriterien nicht erfüllt",
                    tint = Color(0xFFF44336)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kriterien nicht erfüllt", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(4.dp))
            filterViolations.forEach {
                Text("\u2022 $it", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}