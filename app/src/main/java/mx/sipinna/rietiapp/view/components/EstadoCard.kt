/**
 * Componentes de estado de seguimiento para la aplicación RIETI.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package mx.sipinna.rietiapp.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Tarjeta indicadora del nivel o estado de progreso en la consulta de folios.
 *
 * @param numero Número de la etapa en la secuencia.
 * @param titulo Etiqueta descriptiva del paso de seguimiento.
 * @param activo Indicador de si la etapa se encuentra activa actualmente.
 */
@Composable
fun EstadoCard(
    numero: String,
    titulo: String,
    activo: Boolean
) {
    val bgColor = if (activo) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    val shadowAlpha = if (activo) 0.3f else 0.0f
    val iconBgColor = if (activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
    val iconTextColor = if (activo) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    val titleColor = if (activo) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(if (activo) 6.dp else 0.dp, RoundedCornerShape(16.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = shadowAlpha)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                if (activo) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completado",
                        tint = iconTextColor,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = numero,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = iconTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = titulo,
                fontSize = 16.sp,
                fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium,
                color = titleColor
            )
        }
    }
}
