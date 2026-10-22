package mx.sipinna.rietiapp.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ReporteViewModelTest {

    private lateinit var viewModel: ReporteViewModel

    @Before
    fun setUp() {
        viewModel = ReporteViewModel(repository = io.mockk.mockk(relaxed = true))
    }

    // --- TC-01, TC-03, TC-04: Llenado de Formulario y Estado Inmutable ---
    @Test
    fun `TC01, TC03, TC04 - updateState modifica el UiState de forma inmutable`() {
        viewModel.updateState { state ->
            state.copy(
                descripcion = "Reporte de prueba en vía pública",
                modalidad = "SEGUIMIENTO",
                correo = "ciudadano@test.com"
            )
        }

        assertEquals("Reporte de prueba en vía pública", viewModel.uiState.descripcion)
        assertEquals("SEGUIMIENTO", viewModel.uiState.modalidad)
        assertEquals("ciudadano@test.com", viewModel.uiState.correo)
    }

    // --- TC-02, TC-07, TC-08: Resolución y Actualización de Ubicación GPS ---
    @Test
    fun `TC02, TC07, TC08 - resolverYActualizarUbicacion actualiza latitud longitud y direccion`() {
        val lat = 19.6097
        val lng = -99.0600
        val direccion = "Av. Central, Ecatepec"

        viewModel.resolverYActualizarUbicacion(
            lat = lat,
            lng = lng,
            direccion = direccion,
            municipioDetectado = "Ecatepec de Morelos"
        )

        assertEquals(lat, viewModel.uiState.latitud, 0.0001)
        assertEquals(lng, viewModel.uiState.longitud, 0.0001)
        assertEquals(direccion, viewModel.uiState.direccion)
    }
}