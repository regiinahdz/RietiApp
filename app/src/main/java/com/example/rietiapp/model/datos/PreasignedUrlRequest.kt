package com.example.rietiapp.model.datos

/**
 * Solicitud enviada al backend para obtener una URL firmada de carga para Amazon S3.
 *
 * @property folio Folio asignado al reporte.
 * @property nombreArchivo Nombre del archivo de imagen a guardar.
 * @property fileType Tipo MIME del archivo.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class PresignedUrlRequest(
    val folio: String,
    val nombreArchivo: String,
    val fileType: String = "image/jpeg"
)
