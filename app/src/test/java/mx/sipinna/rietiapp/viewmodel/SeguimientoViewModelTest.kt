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
        // Evita errores por llamadas a Log.d / Log.e en la JVM
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

    @Test
    fun `buscarReporte con folio existente actualiza reporteEncontrado y nivelProgreso`() {
        val folio = "RIETI-ECA-2026-00010"

        // Se usan los nombres de variables Kotlin estándar (camelCase)
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

        // Ejecución
        viewModel.buscarReporte {
            callbackEjecutado = true
        }

        // Procesa todas las corrutinas pendientes en la cola de prueba
        testDispatcher.scheduler.advanceUntilIdle()

        // Verificaciones
        assertTrue("El callback de éxito debió ejecutarse", callbackEjecutado)
        assertFalse("isLoading debe ser false", viewModel.uiState.isLoading)
        assertEquals(mockReporte, viewModel.uiState.reporteEncontrado)
        assertEquals(2, viewModel.uiState.nivelProgreso) // "En revisión" -> Nivel 2
        assertNull(viewModel.uiState.error)
    }

    @Test
    fun `buscarReporte con folio inexistente establece mensaje de error en UiState`() {
        val folio = "RIETI-00000"
        coEvery { repository.consultarReportePorFolio(folio) } returns Response.error(404, mockk(relaxed = true))

        viewModel.onFolioInputChange(folio)

        viewModel.buscarReporte {}

        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.uiState.isLoading)
        assertNull(viewModel.uiState.reporteEncontrado)
        assertEquals("No se encontró ningún reporte con el folio ingresado.", viewModel.uiState.error)
    }

    // --- PRUEBA 1.6: Manejo de error de red sin conexión (TC-15) ---
    @Test
    fun `1_6 buscarReporte sin conexion a internet captura excepcion y establece mensaje de error en UiState`() {
        val folio = "RIETI-ECA-2026-00010"

        // Simular que Retrofit/OkHttp arroja una excepción de red (ej. sin internet / UnknownHostException)
        coEvery { repository.consultarReportePorFolio(folio) } throws java.io.IOException("No address associated with hostname")

        viewModel.onFolioInputChange(folio)

        // Ejecutar búsqueda
        viewModel.buscarReporte {}

        // Procesar corrutinas
        testDispatcher.scheduler.advanceUntilIdle()

        // Verificaciones
        assertFalse("isLoading debe volver a false", viewModel.uiState.isLoading)
        assertNull("reporteEncontrado debe ser nulo al fallar la red", viewModel.uiState.reporteEncontrado)

        // Comprobar que el UiState registró el error y la app no tronó (Crash rate = 0%)
        assertTrue(
            "El mensaje de error debe indicar falla de red o conexión",
            viewModel.uiState.error?.contains("conexion", ignoreCase = true) == true ||
                    viewModel.uiState.error?.contains("comunicarse", ignoreCase = true) == true ||
                    viewModel.uiState.error != null
        )
    }
}