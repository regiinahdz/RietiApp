package com.example.rietiapp.view.screens.formularioPantallas

    import android.app.TimePickerDialog
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FormularioScreen(
        onEnviarClick: () -> Unit
    ) {

        var pasoActual by remember { mutableStateOf(0) }

        var descripcion by remember { mutableStateOf("") }
        var municipio by remember { mutableStateOf("") }
        var colonia by remember { mutableStateOf("") }

        var cantidad by remember { mutableStateOf("") }
        var hora by remember { mutableStateOf("") }
        var riesgo by remember { mutableStateOf("") }

    // Edad
        var expandedEdad by remember { mutableStateOf(false) }
        var edadSeleccionada by remember { mutableStateOf("") }


        val edades = listOf(
            "0 a 5 años",
            "6 a 11 años",
            "12 a 17 años"
        )

    // Género
        var expandedGenero by remember { mutableStateOf(false) }
        var generoSeleccionado by remember { mutableStateOf("") }

        val generos = listOf(
            "Femenino",
            "Masculino",
            "No identificado"
        )

    // Actividad
        var expandedActividad by remember { mutableStateOf(false) }
        var actividadSeleccionada by remember { mutableStateOf("") }

        val actividades = listOf(
            "Mendicidad forzada",
            "Explotación sexual",
            "Trata de personas",
            "Utilización para actividades ilícitas",
            "Trabajo peligroso",
            "Otra situación de explotación y/o vulneración"
        )

        val context = LocalContext.current


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

                        when (pasoActual) {

                            0 -> {

                                Card(
                                    modifier = Modifier.fillMaxWidth()
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
                                            text = "Proporciona informacion aproximada. No es necesario que conozcas datos exactos.",
                                            style = MaterialTheme.typography.bodyMedium
                                        )

                                        Spacer(modifier = Modifier.height(24.dp))

                                        OutlinedTextField(
                                            value = cantidad,
                                            onValueChange = { cantidad = it },
                                            label = {
                                                Text("¿Cuántas niñas, niños o adolescentes observaste?")
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        Spacer(modifier = Modifier.height(24.dp))

                                        // EDAD

                                        ExposedDropdownMenuBox(
                                            expanded = expandedEdad,
                                            onExpandedChange = {
                                                expandedEdad = !expandedEdad
                                            }
                                        ) {

                                            OutlinedTextField(
                                                value = edadSeleccionada,
                                                onValueChange = {},
                                                readOnly = true,
                                                label = { Text("Edad aproximada") },
                                                trailingIcon = {
                                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                                        expanded = expandedEdad
                                                    )
                                                },
                                                modifier = Modifier
                                                    .menuAnchor()
                                                    .fillMaxWidth()
                                            )

                                            ExposedDropdownMenu(
                                                expanded = expandedEdad,
                                                onDismissRequest = {
                                                    expandedEdad = false
                                                }
                                            ) {

                                                edades.forEach { edad ->

                                                    DropdownMenuItem(
                                                        text = { Text(edad) },
                                                        onClick = {
                                                            edadSeleccionada = edad
                                                            expandedEdad = false
                                                        }
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))

                                        // GENERO

                                        ExposedDropdownMenuBox(
                                            expanded = expandedGenero,
                                            onExpandedChange = {
                                                expandedGenero = !expandedGenero
                                            }
                                        ) {

                                            OutlinedTextField(
                                                value = generoSeleccionado,
                                                onValueChange = {},
                                                readOnly = true,
                                                label = { Text("Género observado") },
                                                trailingIcon = {
                                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                                        expanded = expandedGenero
                                                    )
                                                },
                                                modifier = Modifier
                                                    .menuAnchor()
                                                    .fillMaxWidth()
                                            )

                                            ExposedDropdownMenu(
                                                expanded = expandedGenero,
                                                onDismissRequest = {
                                                    expandedGenero = false
                                                }
                                            ) {

                                                generos.forEach { genero ->

                                                    DropdownMenuItem(
                                                        text = { Text(genero) },
                                                        onClick = {
                                                            generoSeleccionado = genero
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
                                            onExpandedChange = {
                                                expandedActividad = !expandedActividad
                                            }
                                        ) {

                                            OutlinedTextField(
                                                value = actividadSeleccionada,
                                                onValueChange = {},
                                                readOnly = true,
                                                label = {
                                                    Text("¿Qué actividad estaban realizando?")
                                                },
                                                trailingIcon = {
                                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                                        expanded = expandedActividad
                                                    )
                                                },
                                                modifier = Modifier
                                                    .menuAnchor()
                                                    .fillMaxWidth()
                                            )

                                            ExposedDropdownMenu(
                                                expanded = expandedActividad,
                                                onDismissRequest = {
                                                    expandedActividad = false
                                                }
                                            ) {

                                                actividades.forEach { actividad ->

                                                    DropdownMenuItem(
                                                        text = { Text(actividad) },
                                                        onClick = {
                                                            actividadSeleccionada = actividad
                                                            expandedActividad = false
                                                        }
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(24.dp))

                                        Box(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {

                                            OutlinedTextField(
                                                value = hora,
                                                onValueChange = {},
                                                readOnly = true,
                                                label = {
                                                    Text("¿A qué hora aproximadamente observaste la situación?")
                                                },
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .matchParentSize()
                                                    .clickable {

                                                        val timePicker = TimePickerDialog(
                                                            context,
                                                            { _, hour, minute ->

                                                                hora = String.format(
                                                                    "%02d:%02d",
                                                                    hour,
                                                                    minute
                                                                )
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

                                        Text(
                                            text = "¿Observaste alguna situación de riesgo?"
                                        )

                                        Row {
                                            RadioButton(
                                                selected = riesgo == "Sí",
                                                onClick = {
                                                    riesgo = "Sí"
                                                }
                                            )

                                            Text("Sí")
                                        }
                                    }
                                }
                            }


                            1 -> {

                                Card(
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    Column(
                                        modifier = Modifier.padding(8.dp)
                                    ) {

                                        Text(
                                            text = "2. ¿Dondé ocurrio?",
                                            style = MaterialTheme.typography.titleLarge
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = "Proporciona la ubicación aproximada donde observaste la situación.",
                                            style = MaterialTheme.typography.bodyMedium
                                        )

                                        Spacer(modifier = Modifier.height(24.dp))

                                        Card(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(300.dp)
                                        ) {

                                            Column(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(24.dp),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {

                                                Text(
                                                    text = "📍",
                                                    style = MaterialTheme.typography.displayMedium
                                                )

                                                Spacer(modifier = Modifier.height(16.dp))

                                                Text(
                                                    text = "Mapa interactivo",
                                                    style = MaterialTheme.typography.titleLarge
                                                )

                                                Spacer(modifier = Modifier.height(8.dp))

                                                Text(
                                                    text = "Esta sección permitirá seleccionar y ajustar la ubicación exacta del reporte sobre un mapa.",
                                                    textAlign = TextAlign.Center
                                                )

                                                Spacer(modifier = Modifier.height(8.dp))

                                                Text(
                                                    text = "Funcionalidad en desarrollo.",
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(24.dp))

                                    Text(
                                        text = "Ubicación seleccionada:"
                                    )

                                    Text(
                                        text = "Pendiente"
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                }
                            }


                            2 -> {

                                Card(
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    Column(
                                        modifier = Modifier.padding(8.dp)
                                    ) {

                                        Text(
                                            text = "3. Revisa y envia",
                                            style = MaterialTheme.typography.titleLarge
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = "Verifica que la informacion proporcionada sea correcta.",
                                            style = MaterialTheme.typography.bodyMedium
                                        )

                                        Spacer(modifier = Modifier.height(24.dp))

                                        Card(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {

                                            Column(
                                                modifier = Modifier.padding(20.dp),
                                                verticalArrangement = Arrangement.spacedBy(12.dp)
                                            ) {

                                                Text(
                                                    text = "Resumen de la información",
                                                    style = MaterialTheme.typography.titleMedium
                                                )

                                                HorizontalDivider()

                                                Text(
                                                    text = "Cantidad observada",
                                                    style = MaterialTheme.typography.labelMedium
                                                )

                                                Text(cantidad)

                                                Text(
                                                    text = "Edad aproximada",
                                                    style = MaterialTheme.typography.labelMedium
                                                )

                                                Text(edadSeleccionada)

                                                Text(
                                                    text = "Género observado",
                                                    style = MaterialTheme.typography.labelMedium
                                                )

                                                Text(generoSeleccionado)

                                                Text(
                                                    text = "Actividad realizada",
                                                    style = MaterialTheme.typography.labelMedium
                                                )

                                                Text(actividadSeleccionada)

                                                Text(
                                                    text = "Hora aproximada",
                                                    style = MaterialTheme.typography.labelMedium
                                                )

                                                Text(hora)

                                                Text(
                                                    text = "Situación de riesgo",
                                                    style = MaterialTheme.typography.labelMedium
                                                )

                                                Text(riesgo)

                                                Text(
                                                    text = "Ubicación",
                                                    style = MaterialTheme.typography.labelMedium
                                                )

                                                Text("Mapa pendiente")
                                            }
                                        }
                                    }
                                }
                            }

                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            if (pasoActual > 0) {

                                OutlinedButton(
                                    onClick = {
                                        pasoActual--
                                    },
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

                                Text(
                                    if (pasoActual == 2)
                                        "Enviar"
                                    else
                                        "Continuar"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
