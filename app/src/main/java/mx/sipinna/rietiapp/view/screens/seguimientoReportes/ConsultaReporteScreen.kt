package mx.sipinna.rietiapp.view.screens.seguimientoReportes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.sipinna.rietiapp.view.components.EstadoCard
import mx.sipinna.rietiapp.viewmodel.SeguimientoViewModel

@Composable
fun ConsultaReporteScreen(
    viewModel: SeguimientoViewModel,
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiState
    val reporte = uiState.reporteEncontrado

    if (reporte == null) return

    val scrollState = rememberScrollState()
    androidx.compose.runtime.LaunchedEffect(Unit) {
        scrollState.scrollTo(0)
    }

    val bgColor = MaterialTheme.colorScheme.primary

    // Evaluación de estados especiales
    val estadoLimpio = reporte.estado.trim().lowercase()
    val esCancelado = estadoLimpio == "cancelado"
    val esReincidencia = estadoLimpio in listOf("reincidencia", "reincidente")

    // Colores dinámicos para la tarjeta de Estado Actual según el estatus
    val colorEstado = when {
        esCancelado -> MaterialTheme.colorScheme.error
        esReincidencia -> Color(0xFFE65100) // Ámbar / Naranja de advertencia
        else -> MaterialTheme.colorScheme.primary
    }

    val colorFondoEstado = when {
        esCancelado -> MaterialTheme.colorScheme.errorContainer
        esReincidencia -> Color(0xFFFFF3E0)
        else -> MaterialTheme.colorScheme.surface
    }

    Box(modifier = Modifier.fillMaxSize().background(bgColor)) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    text = "Estado del Reporte",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .shadow(16.dp, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    // Tarjeta ESTADO ACTUAL
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(20.dp), spotColor = colorEstado.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = colorFondoEstado)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "ESTADO ACTUAL",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (esCancelado) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                if (esReincidencia) {
                                    Surface(
                                        color = Color(0xFFFFE0B2),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "Reactivado",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFE65100),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = reporte.estado,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = colorEstado
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.descripcionEstatus,
                                fontSize = 14.sp,
                                color = if (esCancelado) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Detalle del reporte
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(20.dp), spotColor = Color.LightGray),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            DetalleItem(label = "Folio", value = reporte.folio)
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            DetalleItem(label = "Fecha de recepción", value = reporte.fechaFormateada)
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            DetalleItem(label = "Municipio de atención", value = reporte.municipio.ifBlank { "No asignado" })
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Progreso del reporte", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)

                    // Mantenemos únicamente las 5 tarjetas principales
                    EstadoCard(numero = "1", titulo = "Recibido", activo = uiState.nivelProgreso >= 1)
                    EstadoCard(numero = "2", titulo = "En revisión", activo = uiState.nivelProgreso >= 2)
                    EstadoCard(numero = "3", titulo = "En proceso", activo = uiState.nivelProgreso >= 3)
                    EstadoCard(numero = "4", titulo = "Canalizado", activo = uiState.nivelProgreso >= 4)
                    EstadoCard(numero = "5", titulo = "Concluido", activo = uiState.nivelProgreso >= 5)

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun DetalleItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}