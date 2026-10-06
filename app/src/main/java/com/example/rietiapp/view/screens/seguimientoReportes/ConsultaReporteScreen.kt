package com.example.rietiapp.view.screens.seguimientoReportes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rietiapp.view.components.EstadoCard


@Composable
fun ConsultaReporteScreen(
    folio: String,
    estado: String,
    municipio: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Estado de tu reporte",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Consulta el avance actual de tu reporte.",
            style = MaterialTheme.typography.bodyMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Estado actual",
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = estado,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = obtenerDescripcionEstado(estado)
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Folio",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(
                    text = folio,
                    style = MaterialTheme.typography.titleMedium
                )

                HorizontalDivider()

                Text(
                    text = "Fecha de recepción",
                    style = MaterialTheme.typography.labelLarge
                )

                Text("26/08/2026")

                HorizontalDivider()

                Text(
                    text = "Municipio de atención",
                    style = MaterialTheme.typography.labelLarge
                )

                Text(municipio)
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Progreso del reporte",
            style = MaterialTheme.typography.titleMedium
        )

        EstadoCard(
            numero = "1",
            titulo = "Recibido",
            activo = true
        )

        EstadoCard(
            numero = "2",
            titulo = "En revisión",
            activo = true
        )

        EstadoCard(
            numero = "3",
            titulo = "En seguimiento",
            activo = false
        )

        EstadoCard(
            numero = "4",
            titulo = "Canalizado",
            activo = false
        )

        EstadoCard(
            numero = "4",
            titulo = "Concluido",
            activo = false
        )


    }
}