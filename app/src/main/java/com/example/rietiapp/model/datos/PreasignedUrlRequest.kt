package com.example.rietiapp.model.datos

data class PresignedUrlRequest(
    val folio: String,
    val nombreArchivo: String,
    val fileType: String = "image/jpeg"
)