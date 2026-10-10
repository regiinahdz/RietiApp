package com.example.rietiapp.view.screens.formularioPantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rietiapp.view.components.RietiSelectableCard
import com.example.rietiapp.viewmodel.ReporteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoReporteScreen(
    viewModel: ReporteViewModel = viewModel(),
    onContinuarClick: () -> Unit
) {
    val uiState = viewModel.uiState

    // 1. Cargar la selección previa si el usuario ya la había guardado en el ViewModel
    var seleccion by remember {
        mutableStateOf(if (uiState.modalidad == "SEGUIMIENTO") "seguimiento" else "anonimo")
    }

    var correo by remember { mutableStateOf(uiState.correo) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Puedes realizar tu reporte de forma anónima o proporcionar un correo electrónico para recibir seguimiento."
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            RietiSelectableCard(
                titulo = "Reporte anónimo",
                descripcion = "No se solicitarán datos de contacto.",
                selected = seleccion == "anonimo",
                onClick = {
                    seleccion = "anonimo"
                }
            )

            RietiSelectableCard(
                titulo = "Reporte con seguimiento",
                descripcion = "Recibe actualizaciones del reporte por correo.",
                selected = seleccion == "seguimiento",
                onClick = {
                    seleccion = "seguimiento"
                }
            )

            Box(modifier = Modifier.height(100.dp)) {
                if (seleccion == "seguimiento") {
                    OutlinedTextField(
                        value = correo,
                        onValueChange = {
                            correo = it
                        },
                        label = {
                            Text("Correo electrónico")
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    // 2. ACTUALIZAR EL VIEWMODEL COMPARTIDO ANTES DE CONTINUAR
                    val modalidadValida = if (seleccion == "seguimiento") "SEGUIMIENTO" else "ANONIMO"
                    val correoValido = if (seleccion == "seguimiento") correo.trim() else ""

                    viewModel.updateState { estadoActual ->
                        estadoActual.copy(
                            modalidad = modalidadValida,
                            correo = correoValido
                        )
                    }

                    // 3. Continuar a la siguiente pantalla
                    onContinuarClick()
                },
                enabled = seleccion == "anonimo" || correo.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }
        }
    }
}