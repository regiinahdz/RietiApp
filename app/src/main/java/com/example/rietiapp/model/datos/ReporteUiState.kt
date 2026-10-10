package com.example.rietiapp.model.datos

import android.net.Uri

data class ReporteUiState(

    // Tipo de reporte
    val modalidad: String = "ANONIMO",
    val correo: String = "",

    // Catálogos
    val idMunicipio: Int = 0,
    val idCatalogoActividad: Int = 0,

    // Información observada
    val cantidadSeleccionada: String = "1",
    val edadSeleccionada: String = "No especificado",
    val generoSeleccionado: String = "No especificado",
    val actividadSeleccionada: String = "",

    // Formato HH:mm:ss
    val hora: String = "",

    val descripcion: String = "",
    val riesgo: String = "",

    // Ubicación
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,

    val direccion: String = "",

    val calle: String = "",
    val colonia: String = "",

    val cp: String = "",
    val referencias: String = "",

    // Aviso de privacidad
    val aceptoAviso: Boolean = true,

    // Respuesta backend
    val folio: String = "",
    val estatus: String = "",

    // --- AGREGAR ESTOS CAMPOS FALTANTES ---
    val fotosUris: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)