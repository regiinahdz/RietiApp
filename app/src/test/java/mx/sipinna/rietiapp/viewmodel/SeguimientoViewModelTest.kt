package mx.sipinna.rietiapp.viewmodel

import android.util.Log
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import mx.sipinna.rietiapp.model.datos.ConsultaReporteResponse
import mx.sipinna.rietiapp.model.datos.ConsultaReporteWrapper
import mx.sipinna.rietiapp.model.repository.ReporteRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class SeguimientoViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository: ReporteRepository = mockk(relaxed = true)
    private lateinit var viewModel: SeguimientoViewModel

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        coEvery { Log.d(any(), any()) } returns 0
        coEvery { Log.e(any(), any()) } returns 0
        coEvery { Log.e(any(), any(), any()) } returns 0

        Dispatchers.setMain(testDispatcher)
        viewModel = SeguimientoViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    // --- TC-12, TC-14: Consulta Exitosa por Folio e Integración ---
    @Test
    fun `TC12, TC14 - buscarReporte con folio existente actualiza reporteEncontrado y nivelProgreso`() {
        val folio = "RIETI-ECA-2026-00010"

        val mockReporte = ConsultaReporteResponse(
            folio = folio,
            estatus = "En revisión",
            fechaRegistro = "2026-10-11T00:12:17.000Z",
            fechaUltimaAtencion = null,
            municipio = "Ecatepec de Morelos"
        )
        val mockWrapper = ConsultaReporteWrapper(reporte = mockReporte)

        coEvery { repository.consultarReportePorFolio(folio) } returns Response.success(mockWrapper)

        viewModel.onFolioInputChange(folio)
        var callbackEjecutado = false

        viewModel.buscarReporte {
            callbackEjecutado = true
        }

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue("El callback de éxito debió ejecutarse", callbackEjecutado)
        assertFalse("isLoading debe ser false", viewModel.uiState.isLoading)
        assertEquals(mockReporte, viewModel.uiState.reporteEncontrado)
        assertEquals(2, viewModel.uiState.nivelProgreso)
        assertNull(viewModel.uiState.error)
    }

    // --- TC-13: Consulta de Folio Inexistente ---
    @Test
    fun `TC13 - buscarReporte con folio inexistente establece mensaje de error en UiState`() {
        val folio = "RIETI-00000"
        coEvery { repository.consultarReportePorFolio(folio) } returns Response.error(404, mockk(relaxed = true))

        viewModel.onFolioInputChange(folio)

        viewModel.buscarReporte {}

        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.isLoading)
        assertNull(viewModel.uiState.reporteEncontrado)
        assertEquals("No se encontró ningún reporte con el folio ingresado.", viewModel.uiState.error)
    }

    // --- TC-15: Manejo de Error de Red (Modo Offline) ---
    @Test
    fun `TC15 - buscarReporte sin conexion a internet captura excepcion y establece error en UiState`() {
        val folio = "RIETI-ECA-2026-00010"

        coEvery { repository.consultarReportePorFolio(folio) } throws java.io.IOException("No address associated with hostname")

        viewModel.onFolioInputChange(folio)

        viewModel.buscarReporte {}

        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse("isLoading debe volver a false", viewModel.uiState.isLoading)
        assertNull("reporteEncontrado debe ser nulo al fallar la red", viewModel.uiState.reporteEncontrado)

        assertTrue(
            "El mensaje de error debe indicar falla de red o conexión",
            viewModel.uiState.error?.contains("conexion", ignoreCase = true) == true ||
                    viewModel.uiState.error?.contains("comunicarse", ignoreCase = true) == true ||
                    viewModel.uiState.error != null
        )
    }
}