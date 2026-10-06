package com.example.rietiapp.view.screens.seguimientoReportes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SeguimientoScreen(
    onConsultarClick: () -> Unit
) {

    var folio by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Seguimiento de reporte",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Ingresa el folio de tu reporte"
        )

        OutlinedTextField(
            value = folio,
            onValueChange = { folio = it },
            label = { Text("Folio") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onConsultarClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar")
        }
    }
}

