package com.example.rietiapp.view.screens.formularioPantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rietiapp.view.components.RietiButton
import com.example.rietiapp.viewmodel.ReporteViewModel

@Composable
fun ConfirmacionScreen(viewModel: ReporteViewModel,
    onInicioClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "¡Gracias por ayudar a proteger los derechos de niñas, niños y adolescentes!",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Tu reporte fue recibido correctamente.",
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Folio"
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = viewModel.uiState.folio,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Conserva este folio para futuras consultas.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        RietiButton(
            text = "Regresar al inicio",
            onClick = onInicioClick
        )
    }
}