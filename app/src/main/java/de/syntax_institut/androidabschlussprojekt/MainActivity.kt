package de.syntax_institut.androidabschlussprojekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import de.syntax_institut.androidabschlussprojekt.navigation.AppStart
import de.syntax_institut.androidabschlussprojekt.ui.theme.AndroidAbschlussprojektTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidAbschlussprojektTheme {
                AppStart()
            }
        }
    }
}

