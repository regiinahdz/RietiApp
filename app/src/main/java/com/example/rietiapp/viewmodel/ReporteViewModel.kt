package com.example.rietiapp.viewmodel

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

class ReporteViewModel : ViewModel() {

    private val repository =
        ReporteRepository()

    var uiState by mutableStateOf(
        ReporteUiState(
            idMunicipio = 1,
            idCatalogoActividad = 1,
            latitud = 0.0,
            longitud = 0.0,
            aceptoAviso = true
        )
    )
        private set


    fun actualizarCantidad(
        cantidad: String
    ) {
        uiState = uiState.copy(
            cantidadSeleccionada = cantidad
        )
    }

    fun actualizarEdad(
        edad: String
    ) {
        uiState = uiState.copy(
            edadSeleccionada = edad
        )
    }

    fun actualizarGenero(
        genero: String
    ) {
        uiState = uiState.copy(
            generoSeleccionado = genero
        )
    }

    fun actualizarActividad(
        actividad: String
    ) {
        uiState = uiState.copy(
            actividadSeleccionada = actividad
        )
    }

    fun actualizarHora(
        hora: String
    ) {
        uiState = uiState.copy(
            actividadSeleccionada = hora
        )
    }

    fun actualizarDescripcion(
        descripcion: String
    ) {
        uiState = uiState.copy(
            descripcion = descripcion
        )
    }

    fun actualizarCorreo(
        correo: String
    ) {
        uiState = uiState.copy(
            correo = correo
        )
    }

    fun actualizarFolio(
        valor: String
    ) {
        uiState =
            uiState.copy(
                folio = valor
            )
    }

    fun actualizarEstatus(
        valor: String
    ) {
        uiState =
            uiState.copy(
                estatus = valor
            )
    }

    fun actualizarUbicacion(
        latitud: Double,
        longitud: Double,
        direccion: String
    ) {

        uiState = uiState.copy(
            latitud = latitud,
            longitud = longitud,
            direccion = direccion
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun enviarReporte(
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            val request = construirRequest()

            val respuesta =
                repository.crearReporte(request)

            respuesta.body()?.let {

                uiState = uiState.copy(
                    folio = it.folio,
                    estatus = it.estatus
                )

                onSuccess()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun construirRequest(): ReporteRequest {

        return ReporteRequest(

            idMunicipio = uiState.idMunicipio,

            idCatalogoActividad = uiState.idCatalogoActividad,

            modalidad = uiState.modalidad,

            correo_contacto =
                if (uiState.modalidad == "ANONIMO")
                    null
                else
                    uiState.correo.ifBlank { null },

            num_menores =
                uiState.cantidadSeleccionada
                    .toIntOrNull()
                    ?: 1,

            rango_edad =
                uiState.edadSeleccionada.ifBlank {
                    "No especificado"
                },

            genero_observado =
                uiState.generoSeleccionado.ifBlank {
                    "No especificado"
                },

            hora_observada =
                uiState.hora.ifBlank {
                    java.time.LocalTime.now()
                        .withNano(0)
                        .toString()
                },

            descripcion = uiState.descripcion,

            situacion_riesgo =
                uiState.riesgo == "Sí",

            latitud = uiState.latitud,

            longitud = uiState.longitud,

            calle =
                uiState.calle.ifBlank {
                    "No especificada"
                },

            colonia =
                uiState.colonia.ifBlank {
                    "No especificada"
                },

            cp =
                uiState.cp.ifBlank {
                    null
                },

            referencias =
                uiState.referencias.ifBlank {
                    null
                },

            acepto_aviso = true
        )
    }
}