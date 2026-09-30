package com.example.rietiapp.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

@Composable
fun PeligroInmediatoCard() {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "¿La niña, niño o adolescente se encuentra en peligro inmediato?",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Si existe un riesgo inmediato para su vida, integridad o seguridad, comunícate con los servicios de emergencia."
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text("Centro de Comando y Control (C4)")

                    Text(
                        text = "55 0000 0000",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            val context = LocalContext.current

            Button(
                onClick = {

                    val intent = Intent(
                        Intent.ACTION_DIAL
                    ).apply {
                        data = Uri.parse("tel:5500000000")
                    }

                    context.startActivity(intent)
                }
            ) {
                Text("Llamar al C4")
            }

            HorizontalDivider()

            Text(
                text = "IMPORTANTE",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "El formulario RIETI no sustituye los servicios de emergencia ni los mecanismos formales de denuncia."
            )

            HorizontalDivider()

            Text(
                text = "Si no existe peligro inmediato, puedes continuar utilizando RIETI para registrar la situación observada."
            )
        }
    }
}