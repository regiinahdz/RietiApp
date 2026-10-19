/**
 * Función utilitaria para la descripción explicativa de cada estatus de reporte.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package mx.sipinna.rietiapp.view.screens.seguimientoReportes

/**
 * Retorna la descripción explicativa para el estado dado de un expediente.
 *
 * @param estado Nombre del estado a consultar.
 * @return Explicación del estado.
 */
fun obtenerDescripcionEstado(
    estado: String
): String {

    return when (estado) {

        "Registrado" ->
            "Expediente recién creado o registrado en el sistema."

        "En revisión" ->
            "La información y documentación están siendo revisadas."

        "En seguimiento" ->
            "Se requiere monitoreo o acciones posteriores."

        "Canalizado" ->
            "El expediente fue enviado a otra área o institución para su atención."

        "Concluido" ->
            "Las acciones correspondientes fueron realizadas y el expediente puede cerrarse."

        "Archivado" ->
            "Expediente concluido que pasa al archivo para consulta posterior."

        "Cancelado" ->
            "El expediente deja de tener continuidad por alguna causa justificada."

        "Reincidente" ->
            "Un expediente previamente concluido vuelve a requerir atención."

        else -> ""
    }
}
