package com.example.rietiapp.view.screens.formularioPantallas.pasos

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Paso3Resumen(
    cantidad: String,
    edadSeleccionada: String,
    generoSeleccionado: String,
    actividadSeleccionada: String,
    hora: String,
    riesgo: String,
    descripcion: String,
    fotosUris: List<Uri>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "3. Revisa y envía",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Verifica que la información proporcionada sea correcta.",
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

                    Text(text = "Cantidad observada", style = MaterialTheme.typography.labelLarge)
                    Text(cantidad)

                    Text(text = "Edad aproximada", style = MaterialTheme.typography.labelLarge)
                    Text(edadSeleccionada)

                    Text(text = "Género observado", style = MaterialTheme.typography.labelLarge)
                    Text(generoSeleccionado)

                    Text(text = "Actividad realizada", style = MaterialTheme.typography.labelMedium)
                    Text(actividadSeleccionada)

                    Text(text = "Hora aproximada", style = MaterialTheme.typography.labelMedium)
                    Text(hora)

                    Text(text = "Situación de riesgo", style = MaterialTheme.typography.labelMedium)
                    Text(riesgo)

                    Text(text = "Breve descripción de lo visto", style = MaterialTheme.typography.labelMedium)
                    Text(descripcion)

                    Text(text = "Ubicación", style = MaterialTheme.typography.labelMedium)
                    Text("Mapa pendiente")

                    Text(text = "Fotografías adjuntas", style = MaterialTheme.typography.labelMedium)
                    Text(if (fotosUris.isNotEmpty()) "${fotosUris.size} foto(s) seleccionada(s)" else "Ninguna")
                }
            }
        }
    }
}