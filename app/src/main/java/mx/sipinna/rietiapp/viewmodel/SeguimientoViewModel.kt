package mx.sipinna.rietiapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.sipinna.rietiapp.model.datos.SeguimientoUiState
import mx.sipinna.rietiapp.model.repository.ReporteRepository
import mx.sipinna.rietiapp.view.screens.seguimientoReportes.obtenerDescripcionEstado
import kotlinx.coroutines.launch

private const val TAG = "SEGUIMIENTO_DEBUG"

/**
 * ViewModel que gestiona el estado y las operaciones para la consulta y seguimiento de reportes.
 *
 * @property repository Repositorio para consultar información del reporte por medio de la API REST.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
class SeguimientoViewModel(
    private val repository: ReporteRepository = ReporteRepository()
) : ViewModel() {

    /**
     * Estado observable de la interfaz de usuario para el flujo de seguimiento.
     */
    var uiState by mutableStateOf(SeguimientoUiState())
        private set

    /**
     * Limpia el estado de búsqueda para iniciar una nueva consulta sin datos residuales.
     */
    fun limpiarEstado() {
        uiState = SeguimientoUiState()
    }

    /**
     * Actualiza el folio ingresado en el campo de búsqueda de la UI.
     *
     * @param nuevoFolio Cadena con el nuevo texto del folio.
     */
    fun onFolioInputChange(nuevoFolio: String) {
        uiState = uiState.copy(folioInput = nuevoFolio, error = null)
    }

    /**
     * Realiza una búsqueda asíncrona del reporte registrado a partir del folio en [uiState].
     *
     * @param onExito Callback invocado si el reporte se localiza con éxito.
     */
    fun buscarReporte(onExito: () -> Unit) {
        val folioLimpio = uiState.folioInput.trim()

        if (folioLimpio.isBlank()) {
            uiState = uiState.copy(error = "Ingresa un número de folio válido")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            Log.d(TAG, "🔍 Intentando consultar folio: '$folioLimpio'")

            try {
                val response = repository.consultarReportePorFolio(folioLimpio)

                Log.d(TAG, "📡 Código de respuesta HTTP: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    val wrapper = response.body()!!
                    val datos = wrapper.reporte

                    Log.d(TAG, "✅ Datos extraídos: $datos")

                    uiState = uiState.copy(
                        isLoading = false,
                        reporteEncontrado = datos,
                        nivelProgreso = calcularNivelProgreso(datos.estatus),
                        descripcionEstatus = obtenerDescripcionEstado(datos.estatus)
                    )
                    onExito()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "❌ Error en el servidor (${response.code()}): $errorBody")

                    uiState = uiState.copy(
                        isLoading = false,
                        error = "No se encontró ningún reporte con el folio ingresado."
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 Excepción de red o parseo: ${e.message}", e)

                uiState = uiState.copy(
                    isLoading = false,
                    error = "Error de conexión. Verifica tu acceso a internet."
                )
            }
        }
    }

    /**
     * Determina el índice numérico de la etapa de progreso a partir de la cadena de estatus de la API.
     *
     * @param estado Etiqueta textual del estado.
     * @return Nivel numérico correspondiente en la escala de 1 a 5.
     */
    private fun calcularNivelProgreso(estado: String): Int {
        return when (estado.lowercase().trim()) {
            "recibido", "registrado" -> 1
            "en revisión", "en revision" -> 2
            "en proceso", "en seguimiento" -> 3
            "canalizado" -> 4
            "concluido", "archivado" -> 5
            "reincidencia", "reincidente" -> 3 // Reactiva el avance en etapa 'En proceso'
            "cancelado" -> 0 // No avanza la barra por cancelación
            else -> 1
        }
    }
}
