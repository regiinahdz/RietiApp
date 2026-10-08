package com.example.rietiapp.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RietiTopBar(
    mostrarAtras: Boolean,
    onBackClick: () -> Unit
) {

    TopAppBar(
        title = {
            Text("RIETI")
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1565C0),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),

        navigationIcon = {

            if (mostrarAtras) {

                IconButton(
                    onClick = onBackClick
                ) {

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar"
                    )
                }
            }

        }

    )
}