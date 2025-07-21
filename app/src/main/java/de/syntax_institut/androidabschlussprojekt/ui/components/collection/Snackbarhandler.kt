package de.syntax_institut.androidabschlussprojekt.ui.components.collection


import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.SnackEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductSnackbarHandler(
    snackbarHostState: SnackbarHostState,
    productViewModel: ProductViewModel,
    productRemovedMessage: String,
    productRestoredMessage: String,
    undoLabel: String
) {
    LaunchedEffect(Unit) {
        productViewModel.snackbarEvent.collectLatest { event ->
            when (event) {
                is SnackEvent.ProductRemoved -> {
                    val result = snackbarHostState.showSnackbar(
                        message = productRemovedMessage,
                        actionLabel = undoLabel,
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        productViewModel.undoDelete()
                    }
                }
                is SnackEvent.ProductRestored -> {
                    snackbarHostState.showSnackbar(productRestoredMessage)
                }
            }
        }
    }
}