package com.example.rietiapp.model.datos

import com.google.gson.annotations.SerializedName

/**
 * Envoltorio para la respuesta JSON de consulta de reportes.
 *
 * @property reporte Objeto [ConsultaReporteResponse] con los datos del reporte.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class ConsultaReporteWrapper(
    @SerializedName("reporte") val reporte: ConsultaReporteResponse
)

/**
 * Modelo de datos para la información pública devuelta al consultar un reporte por su folio.
 *
 * @property folio Folio único asignado al reporte.
 * @property estatus Estatus actual del reporte en el sistema.
 * @property fechaRegistro Fecha en la que fue registrado el reporte.
 * @property fechaUltimaAtencion Fecha de la última actualización o atención brindada.
 * @property municipio Nombre del municipio donde se registró el incidente.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class ConsultaReporteResponse(
    @SerializedName("folio") val folio: String = "",
    @SerializedName("estatus") val estatus: String = "",
    @SerializedName("fecha_registro") val fechaRegistro: String? = null,
    @SerializedName("fecha_ultima_atencion") val fechaUltimaAtencion: String? = null,
    @SerializedName("municipio") val municipio: String = ""
) {
    /**
     * Alias para acceder a la propiedad [estatus].
     */
    val estado: String
        get() = estatus

    /**
     * Fecha de registro formateada en estilo `DD/MM/AAAA`.
     */
    val fechaFormateada: String
        get() {
            if (fechaRegistro.isNullOrBlank() || fechaRegistro.length < 10) return "Sin fecha"

            val partes = fechaRegistro.take(10).split("-")
            return if (partes.size == 3) "${partes[2]}/${partes[1]}/${partes[0]}" else fechaRegistro.take(10)
        }
}
