package com.example.rietiapp.model.datos

data class ReporteRequest(
    val idMunicipio: Int,
    val idCatalogoActividad: Int,
    val modalidad: String,
    val correo_contacto: String?,
    val num_menores: Int,
    val rango_edad: String,
    val genero_observado: String,
    val hora_observada: String,
    val descripcion: String,
    val situacion_riesgo: Boolean,
    val latitud: Double,
    val longitud: Double,
    val calle: String,
    val colonia: String,
    val cp: String?,
    val referencias: String?,
    val acepto_aviso: Boolean
)