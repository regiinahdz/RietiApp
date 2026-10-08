package com.example.rietiapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

data class ReporteUiState @RequiresApi(Build.VERSION_CODES.O) constructor(

    val correo: String = "",
    val modalidad: String = "ANONIMO",

    val idMunicipio: Int,
    val idCatalogoActivididad: Int,

    val cantidadSeleccionada: String = "1",
    val edadSeleccionada: String = "No especificado.",
    val generoSeleccionado: String = "No especificado.",
    val actividadSeleccionada: String = "",
    // Formato HH:MM:SS (ejemplo: "14:35:09")
    val hora: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date()),
    val riesgo: String = "",
    val descripcion: String = "",

    val latitud: Double,
    val longitud: Double,
    val calle: String = "No especificado.",
    val colonia: String = "No especificada",
    val cp: String = "",
    val referencias:String = "",
    val aceptoAviso: Boolean,

    val folio: String = "",
    val estatus: String = ""
)