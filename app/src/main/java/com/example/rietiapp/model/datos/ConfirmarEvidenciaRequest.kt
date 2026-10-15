package com.example.rietiapp.model.datos

/**
 * Objeto de transferencia para solicitar la confirmación de una evidencia fotográfica en el backend.
 *
 * @property folio Folio único del reporte al que se asocia la evidencia.
 * @property key Clave o ruta del objeto almacenado en el bucket S3.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class ConfirmarEvidenciaRequest(
    val folio: String,
    val key: String
)
