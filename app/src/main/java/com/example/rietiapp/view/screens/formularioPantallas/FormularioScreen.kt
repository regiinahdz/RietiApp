/**
 * Pantalla orquestadora del formulario de reporte dividido en 3 pasos secuenciales.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package com.example.rietiapp.view.screens.formularioPantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rietiapp.view.screens.formularioPantallas.pasos.Paso1Informacion
import com.example.rietiapp.view.screens.formularioPantallas.pasos.Paso2Ubicacion
import com.example.rietiapp.view.screens.formularioPantallas.pasos.Paso3Resumen
import com.example.rietiapp.viewmodel.ReporteViewModel

/**
 * Pantalla orquestadora principal que administra los pasos 1, 2 y 3 del reporte.
 *
 * @param viewModel ViewModel con el estado del formulario.
 * @param onEnviarClick Callback invocado tras enviar exitosamente el reporte.
 * @param onBackClick Callback para regresar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    viewModel: ReporteViewModel,
    onEnviarClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var pasoActual by remember { mutableIntStateOf(0) }
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(pasoActual) {
        scrollState.scrollTo(0)
    }

    val esPaso1Valido = uiState.cantidadSeleccionada.isNotBlank() &&
            uiState.edadSeleccionada.isNotBlank() &&
            uiState.generoSeleccionado.isNotBlank() &&
            uiState.actividadSeleccionada.isNotBlank() &&
            uiState.descripcion.isNotBlank()

    val esPaso2Valido = uiState.direccion.isNotBlank()

    val esPasoValido = when (pasoActual) {
        0 -> esPaso1Valido
        1 -> esPaso2Valido
        2 -> true
        else -> false
    }

    val headerGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary.copy(alpha = 0.85f))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerGradient)
                .padding(bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (pasoActual > 0) {
                                pasoActual--
                            } else {
                                onBackClick()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Text(
                        text = "Reportar caso",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Paso ${pasoActual + 1} de 3",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                    )
                    Text(
                        text = when(pasoActual) {
                            0 -> "Información general"
                            1 -> "Ubicación y evidencias"
                            else -> "Confirmación y envío"
                        },
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { (pasoActual + 1) / 3f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f),
                    strokeCap = StrokeCap.Round
                )
            }
        }

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val minHeight = maxHeight
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .heightIn(min = minHeight)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .imePadding()
            ) {
                when (pasoActual) {
                    0 -> Paso1Informacion(
                        cantidadSeleccionada = uiState.cantidadSeleccionada,
                        onCantidadChange = { valor ->
                            viewModel.updateState { it.copy(cantidadSeleccionada = valor) }
                        },
                        edadSeleccionada = uiState.edadSeleccionada,
                        onEdadSelected = { valor ->
                            viewModel.updateState { it.copy(edadSeleccionada = valor) }
                        },
                        generoSeleccionado = uiState.generoSeleccionado,
                        onGeneroSelected = { valor ->
                            viewModel.updateState { it.copy(generoSeleccionado = valor) }
                        },
                        actividadSeleccionada = uiState.actividadSeleccionada,
                        onActividadSelected = { valor ->
                            viewModel.updateState { it.copy(actividadSeleccionada = valor) }
                        },
                        hora = uiState.hora,
                        onHoraSelected = { valor ->
                            viewModel.updateState { it.copy(hora = valor) }
                        },
                        riesgo = uiState.riesgo,
                        onRiesgoChange = { valor ->
                            viewModel.updateState { it.copy(riesgo = valor) }
                        },
                        descripcion = uiState.descripcion,
                        onDescripcionChange = { valor ->
                            viewModel.updateState { it.copy(descripcion = valor) }
                        }
                    )

                    1 -> Paso2Ubicacion(
                        referencias = uiState.referencias,
                        onReferenciasChange = { valor ->
                            viewModel.updateState { it.copy(referencias = valor) }
                        },
                        fotosUris = uiState.fotosUris,
                        onFotosChange = { uris ->
                            viewModel.updateState { it.copy(fotosUris = uris) }
                        },
                        ubicacionDireccion = uiState.direccion,
                        onUbicacionChange = { lat, lng, dir, municipioDetectado ->
                            viewModel.resolverYActualizarUbicacion(lat, lng, dir, municipioDetectado)
                        }
                    )

                    2 -> Paso3Resumen(
                        cantidad = uiState.cantidadSeleccionada,
                        edadSeleccionada = uiState.edadSeleccionada,
                        generoSeleccionado = uiState.generoSeleccionado,
                        actividadSeleccionada = uiState.actividadSeleccionada,
                        hora = uiState.hora,
                        riesgo = uiState.riesgo,
                        descripcion = uiState.descripcion,
                        fotosUris = uiState.fotosUris,
                        ubicacionDireccion = uiState.direccion
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (pasoActual > 0) {
                        OutlinedButton(
                            onClick = { pasoActual-- },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Text("Atrás", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }

                    Button(
                        onClick = {
                            if (pasoActual < 2) {
                                pasoActual++
                            } else {
                                viewModel.enviarReporteConEvidencias(context) {
                                    onEnviarClick()
                                }
                            }
                        },
                        enabled = esPasoValido && !uiState.isLoading,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .shadow(
                                elevation = if (esPasoValido && !uiState.isLoading) 8.dp else 0.dp,
                                shape = RoundedCornerShape(16.dp),
                                spotColor = MaterialTheme.colorScheme.primary
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 3.dp
                            )
                        } else {
                            Text(
                                if (pasoActual == 2) "Enviar Reporte" else "Continuar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}
