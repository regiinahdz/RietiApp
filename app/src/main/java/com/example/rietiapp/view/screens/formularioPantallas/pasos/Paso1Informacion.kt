package com.example.rietiapp.view.screens.formularioPantallas.pasos

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Paso1Informacion(
    cantidad: String,
    onCantidadChange: (String) -> Unit,
    edadSeleccionada: String,
    onEdadSelected: (String) -> Unit,
    generoSeleccionado: String,
    onGeneroSelected: (String) -> Unit,
    actividadSeleccionada: String,
    onActividadSelected: (String) -> Unit,
    hora: String,
    onHoraSelected: (String) -> Unit,
    riesgo: String,
    onRiesgoChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var expandedEdad by remember { mutableStateOf(false) }
    var expandedGenero by remember { mutableStateOf(false) }
    var expandedActividad by remember { mutableStateOf(false) }

    val edades = listOf("0 a 5 años", "6 a 11 años", "12 a 17 años")
    val generos = listOf("Femenino", "Masculino", "No identificado")
    val actividades = listOf(
        "Mendicidad forzada",
        "Explotación sexual",
        "Trata de personas",
        "Utilización para actividades ilícitas",
        "Trabajo peligroso",
        "Otra situación de explotación y/o vulneración"
    )

    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "1. Cuéntanos lo que observaste",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Proporciona información aproximada. No es necesario que conozcas datos exactos.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = onCantidadChange,
                label = { Text("¿Cuántas niñas, niños o adolescentes observaste?") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // EDAD
            ExposedDropdownMenuBox(
                expanded = expandedEdad,
                onExpandedChange = { expandedEdad = !expandedEdad }
            ) {
                OutlinedTextField(
                    value = edadSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Edad aproximada") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEdad) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedEdad,
                    onDismissRequest = { expandedEdad = false }
                ) {
                    edades.forEach { edad ->
                        DropdownMenuItem(
                            text = { Text(edad) },
                            onClick = {
                                onEdadSelected(edad)
                                expandedEdad = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // GÉNERO
            ExposedDropdownMenuBox(
                expanded = expandedGenero,
                onExpandedChange = { expandedGenero = !expandedGenero }
            ) {
                OutlinedTextField(
                    value = generoSeleccionado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Género observado") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenero) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedGenero,
                    onDismissRequest = { expandedGenero = false }
                ) {
                    generos.forEach { genero ->
                        DropdownMenuItem(
                            text = { Text(genero) },
                            onClick = {
                                onGeneroSelected(genero)
                                expandedGenero = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ACTIVIDAD
            ExposedDropdownMenuBox(
                expanded = expandedActividad,
                onExpandedChange = { expandedActividad = !expandedActividad }
            ) {
                OutlinedTextField(
                    value = actividadSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("¿Qué actividad estaban realizando?") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedActividad) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedActividad,
                    onDismissRequest = { expandedActividad = false }
                ) {
                    actividades.forEach { actividad ->
                        DropdownMenuItem(
                            text = { Text(actividad) },
                            onClick = {
                                onActividadSelected(actividad)
                                expandedActividad = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // HORA
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = hora,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("¿A qué hora aproximadamente observaste la situación?") },
                    modifier = Modifier.fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable {
                            val timePicker = TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    onHoraSelected(String.format("%02d:%02d", hour, minute))
                                },
                                12,
                                0,
                                false
                            )
                            timePicker.show()
                        }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // RIESGO
            Text(text = "¿Observaste alguna situación de riesgo?")

            Row {
                RadioButton(
                    selected = riesgo == "Sí",
                    onClick = { onRiesgoChange("Sí") }
                )
                Text("Sí")
            }
        }
    }
}