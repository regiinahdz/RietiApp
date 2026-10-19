package mx.sipinna.rietiapp.model.datos

/**
 * Respuesta recibida del servidor tras registrar un nuevo reporte exitosamente.
 *
 * @property message Mensaje informativo del servidor.
 * @property folio Folio único generado para el seguimiento del reporte.
 * @property estatus Estado inicial asignado al reporte en el sistema.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class ReporteResponse(
    val message: String,
    val folio: String,
    val estatus: String
)
