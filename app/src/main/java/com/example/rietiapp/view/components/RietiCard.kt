/**
 * Tarjeta de contenedor con estilo de diseño neumórfico/claymorfismo.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package com.example.rietiapp.view.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rietiapp.ui.theme.clay

/**
 * Contenedor de tarjeta genérico con bordes redondeados y efecto claymorfismo.
 *
 * @param modifier Modificador composable para personalizar el layout.
 * @param backgroundColor Color de fondo del contenedor.
 * @param content Contenido composable interno.
 */
@Composable
fun RietiCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .clay(
                backgroundColor = backgroundColor,
                cornerRadius = 32.dp,
                elevation = 8.dp
            )
    ) {
        content()
    }
}
