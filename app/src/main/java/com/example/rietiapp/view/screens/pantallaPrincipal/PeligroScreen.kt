package com.example.rietiapp.view.screens.pantallaPrincipal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PeligroScreen(
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Situación de peligro inmediato",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Si una niña, niño o adolescente se encuentra en peligro inmediato, comunícate con el número de emergencia."
        )

        Button(
            onClick = {}
        ) {
            Text("Llamar")
        }
    }
}