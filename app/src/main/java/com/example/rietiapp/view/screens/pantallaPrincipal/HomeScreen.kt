package com.example.rietiapp.view.screens.pantallaPrincipal

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rietiapp.R
import com.example.rietiapp.view.components.PeligroInmediatoCard

@Composable
fun HomeScreen(
    onReportarClick: () -> Unit,
    onSeguimientoClick: () -> Unit,
    onPeligroClick: () -> Unit
) {
    var mostrarPeligro by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo_rieti),
                contentDescription = "Logo RIETI",
                modifier = Modifier.size(100.dp)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = "Red Intermunicipal para la Erradicación del Trabajo Infantil",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido al sistema de reporte y seguimiento de casos.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Selecciona una opcion para continuar.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onReportarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text("Reportar caso")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSeguimientoClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text("Dar seguimiento")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        mostrarPeligro = !mostrarPeligro
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Text(
                            text = "Situación de peligro inmediato",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Text(
                        text = if (mostrarPeligro) "▲" else "▼"
                    )
                }
            }

            AnimatedVisibility(
                visible = mostrarPeligro
            ) {
                PeligroInmediatoCard()
            }

            Spacer(modifier = Modifier.height(32.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Versión 1.0",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "RIETI • Gobierno Municipal",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}