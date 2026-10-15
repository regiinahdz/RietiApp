package com.example.rietiapp.model.datos

/**
 * Respuesta del backend con los datos para realizar la carga directa de evidencia a Amazon S3.
 *
 * @property uploadUrl URL presignada para la solicitud HTTP PUT.
 * @property key Clave de ruta asignada al objeto en S3.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class PresignedUrlResponse(
    val uploadUrl: String,
    val key: String
)
