package com.example.rietiapp.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rietiapp.model.datos.ReporteRequest
import com.example.rietiapp.model.repository.ReporteRepository
import kotlinx.coroutines.launch
import com.example.rietiapp.model.datos.Municipio

class ReporteViewModel(
    private val repository: ReporteRepository = ReporteRepository()
) : ViewModel() {

    var uiState by mutableStateOf(ReporteUiState())
        private set

    private var catalogoMunicipios: List<Municipio> = emptyList()

    fun updateState(transform: (ReporteUiState) -> ReporteUiState) {
        uiState = transform(uiState)
    }

    init {
        cargarCatalogoMunicipios()
    }

    private fun cargarCatalogoMunicipios() {
        viewModelScope.launch {
            try {
                val response = repository.obtenerMunicipios()
                if (response.isSuccessful) {
                    catalogoMunicipios = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun resolverYActualizarUbicacion(
        latitud: Double,
        longitud: Double,
        direccion: String,
        nombreMunicipioDetectado: String
    ) {
        // Normaliza el texto para comparar fácilmente ("Atizapán" -> "atizapan")
        val nombreLimpio = normalizarTexto(nombreMunicipioDetectado)

        val municipioEncontrado = catalogoMunicipios.find { municipio ->
            normalizarTexto(municipio.nombre).contains(nombreLimpio) ||
                    nombreLimpio.contains(normalizarTexto(municipio.nombre))
        }

        val idEncontrado = municipioEncontrado?.id ?: uiState.idMunicipio

        updateState {
            it.copy(
                latitud = latitud,
                longitud = longitud,
                direccion = direccion,
                idMunicipio = idEncontrado // ¡Se asigna el ID automáticamente!
            )
        }
    }

    private fun normalizarTexto(texto: String): String {
        return java.text.Normalizer.normalize(texto, java.text.Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
            .lowercase()
            .trim()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun enviarReporteConEvidencias(context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            try {
                // 1. Enviar datos del reporte
                val request = construirRequest()
                val respuestaReporte = repository.crearReporte(request)

                if (respuestaReporte.isSuccessful && respuestaReporte.body() != null) {
                    val body = respuestaReporte.body()!!
                    val folioGenerado = body.folio

                    uiState = uiState.copy(
                        folio = folioGenerado,
                        estatus = body.estatus
                    )

                    // 2. Enviar evidencias/fotografías si existen
                    if (uiState.fotosUris.isNotEmpty()) {
                        for (fotoUri in uiState.fotosUris) {
                            repository.subirFotoEvidencia(context, folioGenerado, fotoUri)
                        }
                    }

                    uiState = uiState.copy(isLoading = false)
                    onSuccess()

                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        error = "Error al crear el reporte: ${respuestaReporte.code()}"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.localizedMessage ?: "Error en la transmisión"
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun construirRequest(): ReporteRequest {
        return ReporteRequest(
            idMunicipio = uiState.idMunicipio,
            idCatalogoActividad = uiState.idCatalogoActividad,
            modalidad = uiState.modalidad,
            correo_contacto = if (uiState.modalidad == "ANONIMO") null else uiState.correo.ifBlank { null },
            num_menores = uiState.cantidadSeleccionada.toIntOrNull() ?: 1,
            rango_edad = uiState.edadSeleccionada.ifBlank { "No especificado" },
            genero_observado = uiState.generoSeleccionado.ifBlank { "No especificado" },
            hora_observada = uiState.hora.ifBlank { java.time.LocalTime.now().withNano(0).toString() },
            descripcion = uiState.descripcion,
            situacion_riesgo = uiState.riesgo == "Sí",
            latitud = uiState.latitud,
            longitud = uiState.longitud,
            calle = uiState.calle.ifBlank { "No especificada" },
            colonia = uiState.colonia.ifBlank { "No especificada" },
            cp = uiState.cp.ifBlank { null },
            referencias = uiState.referencias.ifBlank { null },
            acepto_aviso = uiState.aceptoAviso
        )
    }
}