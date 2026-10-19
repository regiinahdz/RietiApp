package mx.sipinna.rietiapp.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mx.sipinna.rietiapp.ui.theme.clay

@Composable
fun RietiCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.clay(
            borderRadius = 20.dp,
            color = backgroundColor
        )
    ) {
        content()
    }
}
