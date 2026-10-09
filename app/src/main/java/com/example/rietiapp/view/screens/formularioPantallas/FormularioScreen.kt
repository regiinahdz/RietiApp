package com.example.rietiapp.view.screens.formularioPantallas

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rietiapp.view.screens.formularioPantallas.pasos.Paso1Informacion
import com.example.rietiapp.view.screens.formularioPantallas.pasos.Paso2Ubicacion
import com.example.rietiapp.view.screens.formularioPantallas.pasos.Paso3Resumen
import com.example.rietiapp.viewmodel.ReporteViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen( viewModel: ReporteViewModel,
    onEnviarClick: () -> Unit
) {
    // Control de flujo del formulario
    var pasoActual by remember { mutableStateOf(0) }

    //Estado del paso 0: Seguimiento o anonimo
    //var correo by remember { mutableStateOf("") }

    // Estado del Paso 1: Información básica
    var cantidadSeleccionada = viewModel.uiState.cantidadSeleccionada
    var edadSeleccionada = viewModel.uiState.edadSeleccionada
    var generoSeleccionado = viewModel.uiState.generoSeleccionado
    var actividadSeleccionada = viewModel.uiState.actividadSeleccionada
    var hora = viewModel.uiState.hora
    var riesgo = viewModel.uiState.riesgo
    var descripcion = viewModel.uiState.descripcion

    // Estado del Paso 2: Ubicación y Fotografías
    var referencias = viewModel.uiState.referencias
    var fotosUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var ubicacionDireccion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(8.dp)
        ) {
            Text(
                text = "Reportar caso",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    LinearProgressIndicator(
                        progress = { (pasoActual + 1) / 3f },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Paso ${pasoActual + 1} de 3",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Delegación a los archivos correspondientes según el paso actual
                    when (pasoActual) {
                        0 -> Paso1Informacion(
                            cantidadSeleccionada = cantidadSeleccionada,
                            onCantidadChange = { viewModel },
                            edadSeleccionada = edadSeleccionada,
                            onEdadSelected = { viewModel},
                            generoSeleccionado = generoSeleccionado,
                            onGeneroSelected = { viewModel },
                            actividadSeleccionada = actividadSeleccionada,
                            onActividadSelected = { viewModel },
                            hora = hora,
                            onHoraSelected = { viewModel },
                            riesgo = riesgo,
                            descripcion = descripcion,
                            onDescripcionChange = { viewModel },
                            onRiesgoChange = { riesgo = it }
                        )

                        1 -> Paso2Ubicacion(
                            referencias = referencias,
                            onReferenciasChange = { referencias = it },
                            fotosUris = fotosUris,
                            onFotosChange = { fotosUris = it },
                            ubicacionDireccion = ubicacionDireccion,
                            onUbicacionChange = { ubicacionDireccion = it }
                        )

                        2 -> Paso3Resumen(
                            cantidad = cantidadSeleccionada,
                            edadSeleccionada = edadSeleccionada,
                            generoSeleccionado = generoSeleccionado,
                            actividadSeleccionada = actividadSeleccionada,
                            hora = hora,
                            riesgo = riesgo,
                            descripcion = descripcion,
                            fotosUris = fotosUris,
                            ubicacionDireccion = ubicacionDireccion
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botones de navegación (Atrás / Continuar - Enviar)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (pasoActual > 0) {
                            OutlinedButton(
                                onClick = { pasoActual-- },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Atrás")
                            }
                        }

                        Button(
                            onClick = {

                                if (pasoActual < 2) {

                                    pasoActual++

                                } else {

                                    viewModel.enviarReporte {

                                        onEnviarClick()
                                    }
                                }
                            }
                        ) {
                            Text(if (pasoActual == 2) "Enviar" else "Continuar")
                        }
                    }
                }
            }
        }
    }
}