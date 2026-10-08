package com.example.rietiapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ReporteViewModel : ViewModel() {

    fun actualizarFolio(
        nuevoFolio: String
    ) {
        folio = nuevoFolio
    }

    fun actualizarEstatus(
        nuevoEstatus: String
    ) {
        estatus = nuevoEstatus
    }
}