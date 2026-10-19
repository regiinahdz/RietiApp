package mx.sipinna.rietiapp.view.screens.seguimientoReportes

/**
 * Retorna la descripción explicativa para el estado dado de un reporte.
 *
 * @param estado Nombre del estado a consultar.
 * @return Explicación clara del estado.
 */
fun obtenerDescripcionEstado(
    estado: String
): String {
    return when (estado.trim().lowercase()) {
        "recibido", "registrado" ->
            "El reporte ha sido recibido exitosamente y se encuentra registrado."

        "en revisión", "en revision" ->
            "La información y evidencia adjunta están siendo revisadas."

        "en proceso", "en seguimiento" ->
            "Se están realizando las acciones y el seguimiento correspondiente para la atención del reporte."

        "canalizado" ->
            "El reporte fue remitido a la autoridad o institución competente para su atención."

        "concluido", "archivado" ->
            "Las acciones fueron completadas y el reporte ha finalizado su atención."

        "cancelado" ->
            "El reporte ha sido cancelado y finalizado por causa justificada."

        "reincidencia", "reincidente" ->
            "Este reporte previamente cerrado ha sido reabierto debido a una reincidencia registrada."

        else -> "El reporte se encuentra en trámite."
    }
}