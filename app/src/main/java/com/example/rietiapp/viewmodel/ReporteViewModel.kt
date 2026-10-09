package com.example.rietiapp.viewmodel

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
            idCatalogoActivididad = 1,
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

    fun enviarReporte(
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            val request =
                ReporteRequest(

                    idMunicipio = 1,

                    idCatalogoActividad = 1,

                    modalidad = uiState.modalidad,

                    correo_contacto =
                        uiState.correo,

                    num_menores =
                        uiState.cantidadSeleccionada
                            .toIntOrNull()
                            ?: 1,

                    rango_edad =
                        uiState.edadSeleccionada,

                    genero_observado =
                        uiState.generoSeleccionado,

                    hora_observada =
                        uiState.hora,

                    descripcion =
                        uiState.descripcion,

                    situacion_riesgo =
                        uiState.riesgo == "Sí",

                    latitud = 0.0,

                    longitud = 0.0,

                    calle = "No especificada",

                    colonia = "No especificada",

                    cp = "",

                    referencias = "",

                    acepto_aviso = true
                )

            val respuesta =
                repository.crearReporte(
                    request
                )

            respuesta.body()?.let {

                uiState =
                    uiState.copy(
                        folio = it.folio,
                        estatus = it.estatus
                    )

                onSuccess()
            }
        }
    }
}