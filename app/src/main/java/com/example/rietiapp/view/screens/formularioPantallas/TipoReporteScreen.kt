package com.example.rietiapp.view.screens.formularioPantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rietiapp.view.components.RietiSelectableCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipoReporteScreen(
    onContinuarClick: () -> Unit
) {

    var seleccion by remember { mutableStateOf("anonimo") }

    var correo by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Puedes realizar tu reporte de forma anónima o proporcionar un correo electrónico para recibir seguimiento."
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
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
                    modifier = Modifier.fillMaxWidth()
                )
            }}

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onContinuarClick,
                enabled = seleccion == "anonimo" || correo.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }
        }
    }
}
