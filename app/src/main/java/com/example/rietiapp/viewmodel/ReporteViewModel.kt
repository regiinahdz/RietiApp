package com.example.rietiapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ReporteViewModel : ViewModel() {

    var folio by mutableStateOf("")
        private set

    var estatus by mutableStateOf("")
        private set

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