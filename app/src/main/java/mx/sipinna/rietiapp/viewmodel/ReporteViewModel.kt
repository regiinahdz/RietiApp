package mx.sipinna.rietiapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.sipinna.rietiapp.model.datos.ReporteRequest
import mx.sipinna.rietiapp.model.datos.ReporteUiState
import mx.sipinna.rietiapp.model.repository.ReporteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "REPORTE_DEBUG"

/**
 * ViewModel que gestiona el estado y la lógica de negocio para la creación y envío de reportes.
 *
 * @property repository Instancia de [ReporteRepository] para interactuar con las APIs de backend y S3.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
class ReporteViewModel(
    private val repository: ReporteRepository = ReporteRepository()
) : ViewModel() {

    /**
     * Estado UI inmutable expuesto a las vistas.
     */
    var uiState by mutableStateOf(ReporteUiState())
        private set

    /**
     * Aplica una transformación al estado de la interfaz [ReporteUiState].
     *
     * @param transform Función lambda que recibe el estado actual y retorna un nuevo [ReporteUiState].
     */
    fun updateState(transform: (ReporteUiState) -> ReporteUiState) {
        uiState = transform(uiState)
    }

    /**
     * Resuelve la ubicación seleccionada en el mapa.
     *
     * @param lat Latitud geográfica.
     * @param lng Longitud geográfica.
     * @param direccion Texto de la dirección obtenida.
     * @param municipioDetectado Nombre del municipio recuperado por Geocoder.
     */
    fun resolverYActualizarUbicacion(
        lat: Double,
        lng: Double,
        direccion: String,
        municipioDetectado: String
    ) {
        uiState = uiState.copy(
            latitud = lat,
            longitud = lng,
            direccion = direccion
        )
    }

    /**
     * Coordina el envío final del reporte y la subida secuencial de las evidencias en AWS S3.
     *
     * @param context Contexto necesario para acceder a los archivos multimedia.
     * @param onExito Callback ejecutado al completar exitosamente todo el proceso.
     */
    fun enviarReporteConEvidencias(context: Context, onExito: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            val exitoCreacion = registrarReporteEnBackend()

            if (!exitoCreacion) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Fallo al registrar el reporte en el servidor. Verifica los datos."
                )
                return@launch
            }

            val folioGenerado = uiState.folio

            if (uiState.fotosUris.isNotEmpty() && folioGenerado.isNotBlank()) {
                Log.d(TAG, "📷 Subiendo ${uiState.fotosUris.size} fotografía(s) para el folio: $folioGenerado")

                val fotosSubidasExitosamente = withContext(Dispatchers.IO) {
                    var exitoTodas = true
                    for (uri in uiState.fotosUris) {
                        val subidaExitosa = repository.subirFotoEvidencia(context, folioGenerado, uri)
                        if (!subidaExitosa) {
                            Log.e(TAG, "❌ Error al subir la imagen: $uri")
                            exitoTodas = false
                        }
                    }
                    exitoTodas
                }

                if (!fotosSubidasExitosamente) {
                    Log.w(TAG, "⚠️ El reporte se creó pero algunas fotos no se pudieron subir.")
                }
            }

            uiState = uiState.copy(isLoading = false)
            onExito()
        }
    }

    /**
     * Transforma los campos capturados en un [ReporteRequest] y solicita la creación en la API.
     *
     * @return `true` si la llamada HTTP fue exitosa y se obtuvo un folio, `false` en caso de error.
     */
    private suspend fun registrarReporteEnBackend(): Boolean = withContext(Dispatchers.IO) {
        val idActividad = when (uiState.actividadSeleccionada) {
            "Mendicidad forzada" -> 1
            "Explotación sexual" -> 2
            "Trata de personas" -> 3
            "Utilización para actividades ilícitas" -> 4
            "Trabajo peligroso" -> 5
            else -> 6
        }

        val numMenoresInt = when (uiState.cantidadSeleccionada) {
            "1" -> 1
            "2" -> 2
            "3" -> 3
            "4" -> 4
            "5 o más" -> 5
            else -> 1
        }

        val request = ReporteRequest(
            idMunicipio = uiState.idMunicipio.coerceAtLeast(1),
            idCatalogoActividad = idActividad,
            modalidad = uiState.modalidad,
            correo_contacto = uiState.correo.ifBlank { null },
            num_menores = numMenoresInt,
            rango_edad = uiState.edadSeleccionada.ifBlank { "0 a 5 años" },
            genero_observado = uiState.generoSeleccionado.ifBlank { "No identificado" },
            hora_observada = if (uiState.hora.length == 5) "${uiState.hora}:00" else "12:00:00",
            descripcion = uiState.descripcion.ifBlank { "Sin descripción" },
            situacion_riesgo = uiState.riesgo == "Sí",
            latitud = uiState.latitud,
            longitud = uiState.longitud,
            calle = uiState.direccion.ifBlank { "Ubicación en mapa" },
            colonia = "Centro",
            cp = null,
            referencias = uiState.referencias.ifBlank { null },
            acepto_aviso = true
        )

        try {
            Log.d(TAG, "🚀 Enviando ReporteRequest al backend...")
            val response = repository.crearReporte(request)

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Log.d(TAG, "🎉 Reporte creado exitosamente. Folio asignado: ${body.folio}")

                withContext(Dispatchers.Main) {
                    uiState = uiState.copy(folio = body.folio)
                }
                return@withContext true
            } else {
                val err = response.errorBody()?.string()
                Log.e(TAG, "❌ Error al crear el reporte (${response.code()}): $err")
                return@withContext false
            }
        } catch (e: Exception) {
            Log.e(TAG, "💥 Excepción de red al enviar reporte: ${e.message}", e)
            return@withContext false
        }
    }
}
