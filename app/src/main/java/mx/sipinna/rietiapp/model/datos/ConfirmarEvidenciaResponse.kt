package mx.sipinna.rietiapp.model.datos

/**
 * Respuesta del servidor tras procesar la confirmación de una evidencia fotográfica.
 *
 * @property message Mensaje informativo devuelto por el servidor.
 * @property idEvidencia Identificador numérico único generado para la evidencia.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class ConfirmarEvidenciaResponse(
    val message: String,
    val idEvidencia: Int
)
