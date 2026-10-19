package mx.sipinna.rietiapp.model.datos

import android.net.Uri

/**
 * Estado UI inmutable que consolida los datos del formulario de reporte de incidencias.
 *
 * @property modalidad Modalidad del reporte ("ANONIMO" o "SEGUIMIENTO").
 * @property correo Correo de contacto opcional.
 * @property idMunicipio ID del municipio asignado.
 * @property idCatalogoActividad ID de la actividad en el catálogo.
 * @property cantidadSeleccionada Cantidad de menores observada.
 * @property edadSeleccionada Rango de edad seleccionado.
 * @property generoSeleccionado Género observado.
 * @property actividadSeleccionada Actividad descrita.
 * @property hora Hora aproximada.
 * @property descripcion Texto explicativo detallado.
 * @property riesgo Estado de riesgo ("Sí", "No", "No se").
 * @property latitud Coordenada GPS de latitud.
 * @property longitud Coordenada GPS de longitud.
 * @property direccion Dirección textual resultante de geocodificación.
 * @property calle Calle de la ubicación.
 * @property colonia Colonia o zona.
 * @property cp Código postal.
 * @property referencias Referencias del lugar.
 * @property aceptoAviso Estado de aceptación del aviso de privacidad.
 * @property folio Folio retornado por el servidor.
 * @property estatus Estatus del reporte.
 * @property fotosUris Lista de imágenes locales adjuntas.
 * @property isLoading Indicador de carga de peticiones HTTP.
 * @property error Mensaje descriptivo si ocurre un fallo.
 * @property isSending Indicador de envío en progreso.
 * @property folioGenerado Folio asignado.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class ReporteUiState(
    val modalidad: String = "ANONIMO",
    val correo: String = "",

    val idMunicipio: Int = 1,
    val idCatalogoActividad: Int = 0,

    val cantidadSeleccionada: String = "",
    val edadSeleccionada: String = "",
    val generoSeleccionado: String = "",
    val actividadSeleccionada: String = "",

    val hora: String = "",
    val descripcion: String = "",
    val riesgo: String = "",

    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val direccion: String = "",
    val calle: String = "",
    val colonia: String = "",
    val cp: String = "",
    val referencias: String = "",

    val aceptoAviso: Boolean = false,

    val folio: String = "",
    val estatus: String = "",
    val fotosUris: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,

    val isSending: Boolean = false,
    val folioGenerado: String = ""
)
