package com.example.rieti.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EstadoCard(
    numero: String,
    titulo: String,
    activo: Boolean
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = numero,
                style = MaterialTheme.typography.headlineSmall,
                color = if (activo)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = titulo,
                color = if (activo)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline
            )
        }
    }
}