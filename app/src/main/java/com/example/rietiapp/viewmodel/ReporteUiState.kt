package com.example.rietiapp.viewmodel

data class ReporteUiState(

    val correo: String = "",
    val modalidad: String = "ANONIMO",

    val cantidadSeleccionada: String = "",
    val edadSeleccionada: String = "",
    val generoSeleccionado: String = "",
    val actividadSeleccionada: String = "",
    val hora: String = "",
    val riesgo: String = "",
    val descripcion: String = "",

    val folio: String = "",
    val estatus: String = ""
)