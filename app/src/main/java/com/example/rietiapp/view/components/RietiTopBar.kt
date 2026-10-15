/**
 * Barra superior de navegación superior para las pantallas de la aplicación.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package com.example.rietiapp.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * TopAppBar personalizada con soporte para botón de navegación hacia atrás e íconos de acción.
 *
 * @param mostrarAtras `true` si debe mostrar el botón de regreso, `false` de lo contrario.
 * @param onBackClick Callback invocado al presionar la flecha de regreso.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RietiTopBar(
    mostrarAtras: Boolean,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        navigationIcon = {
            if (mostrarAtras) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar"
                    )
                }
            }
        },
        actions = {
            if (mostrarAtras) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Opciones"
                    )
                }
            } else {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil"
                    )
                }
            }
        }
    )
}
