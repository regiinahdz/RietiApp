package com.example.rietiapp.view.screens.formularioPantallas

import android.net.Uri
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    onEnviarClick: () -> Unit
) {
    // Control de flujo del formulario
    var pasoActual by remember { mutableStateOf(0) }

    // Estado del Paso 1: Información básica
    var cantidad by remember { mutableStateOf("") }
    var edadSeleccionada by remember { mutableStateOf("") }
    var generoSeleccionado by remember { mutableStateOf("") }
    var actividadSeleccionada by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var riesgo by remember { mutableStateOf("") }

    // Estado del Paso 2: Ubicación y Fotografías
    var fotosUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

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
                            cantidad = cantidad,
                            onCantidadChange = { cantidad = it },
                            edadSeleccionada = edadSeleccionada,
                            onEdadSelected = { edadSeleccionada = it },
                            generoSeleccionado = generoSeleccionado,
                            onGeneroSelected = { generoSeleccionado = it },
                            actividadSeleccionada = actividadSeleccionada,
                            onActividadSelected = { actividadSeleccionada = it },
                            hora = hora,
                            onHoraSelected = { hora = it },
                            riesgo = riesgo,
                            onRiesgoChange = { riesgo = it }
                        )

                        1 -> Paso2Ubicacion(
                            fotosUris = fotosUris,
                            onFotosChange = { fotosUris = it }
                        )

                        2 -> Paso3Resumen(
                            cantidad = cantidad,
                            edadSeleccionada = edadSeleccionada,
                            generoSeleccionado = generoSeleccionado,
                            actividadSeleccionada = actividadSeleccionada,
                            hora = hora,
                            riesgo = riesgo,
                            fotosUris = fotosUris
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
                                    onEnviarClick()
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (pasoActual == 2) "Enviar" else "Continuar")
                        }
                    }
                }
            }
        }
    }
}