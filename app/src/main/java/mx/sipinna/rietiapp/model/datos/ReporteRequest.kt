package mx.sipinna.rietiapp.model.datos

/**
 * Payload completo enviado a la API REST para la creación de un nuevo reporte.
 *
 * @property idMunicipio ID numérico del municipio seleccionado.
 * @property idCatalogoActividad ID de la actividad observada.
 * @property modalidad Modalidad del reporte ("ANONIMO" o "SEGUIMIENTO").
 * @property correo_contacto Correo electrónico opcional de contacto.
 * @property num_menores Número de menores observados.
 * @property rango_edad Rango de edad aproximado.
 * @property genero_observado Género observado de las personas.
 * @property hora_observada Hora aproximada en formato `HH:mm:ss`.
 * @property descripcion Descripción detallada de los hechos.
 * @property situacion_riesgo `true` si existía riesgo directo, `false` de lo contrario.
 * @property latitud Coordenada GPS de latitud.
 * @property longitud Coordenada GPS de longitud.
 * @property calle Calle del incidente.
 * @property colonia Colonia o vecindario.
 * @property cp Código postal.
 * @property referencias Referencias visuales adicionales de la ubicación.
 * @property acepto_aviso Indicador de aceptación del aviso de privacidad.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class ReporteRequest(
    val idMunicipio: Int,
    val idCatalogoActividad: Int,
    val modalidad: String,
    val correo_contacto: String?,
    val num_menores: Int,
    val rango_edad: String,
    val genero_observado: String,
    val hora_observada: String,
    val descripcion: String,
    val situacion_riesgo: Boolean,
    val latitud: Double,
    val longitud: Double,
    val calle: String,
    val colonia: String,
    val cp: String?,
    val referencias: String?,
    val acepto_aviso: Boolean
)
