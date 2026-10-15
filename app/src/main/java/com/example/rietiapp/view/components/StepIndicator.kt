/**
 * Indicador de pasos para flujos de formularios.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package com.example.rietiapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Componente visual que despliega las etiquetas de avance del formulario secuencial.
 *
 * @param pasoActual Índice del paso actual en el formulario (0, 1 o 2).
 */
@Composable
fun StepIndicator(
    pasoActual: Int
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = if (pasoActual > 0)
                "✓ Situación"
            else
                "● Situación",

            color =
                if (pasoActual >= 0)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline
        )

        Text(
            text = if (pasoActual > 1)
                "✓ Ubicación"
            else if (pasoActual == 1)
                "● Ubicación"
            else
                "○ Ubicación",

            color =
                if (pasoActual >= 1)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline
        )

        Text(
            text =
                if (pasoActual == 2)
                    "● Revisión"
                else
                    "○ Revisión",

            color =
                if (pasoActual == 2)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outline
        )
    }
}
