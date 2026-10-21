package mx.sipinna.rietiapp.viewmodel

import io.mockk.coEvery
import io.mockk.mockk
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
        Dispatchers.setMain(testDispatcher)
        viewModel = SeguimientoViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // --- PRUEBA 1.1: Actualización de input de folio ---
    @Test
    fun `onFolioInputChange actualiza el folioInput y limpia el error`() {
        // Asignar un error previo
        viewModel.onFolioInputChange("")
        viewModel.buscarReporte {} // Genera error por estar vacío

        // Acción
        viewModel.onFolioInputChange("RIETI-ECA-2026-00010")

        // Verificación
        assertEquals("RIETI-ECA-2026-00010", viewModel.uiState.folioInput)
        assertNull("El error debe ser nulo tras ingresar un nuevo valor", viewModel.uiState.error)
    }

    // --- PRUEBA 1.2: Búsqueda con folio vacío ---
    @Test
    fun `buscarReporte con folio vacio asigna mensaje de error sin llamar al repositorio`() {
        viewModel.onFolioInputChange("   ")

        viewModel.buscarReporte {}

        assertEquals("Ingresa un número de folio válido", viewModel.uiState.error)
        assertFalse("isLoading debe ser false", viewModel.uiState.isLoading)
    }

    // --- PRUEBA 1.3: Limpieza de estado ---
    @Test
    fun `limpiarEstado restablece el UiState a su estado inicial`() {
        viewModel.onFolioInputChange("RIETI-12345")

        viewModel.limpiarEstado()

        assertEquals("", viewModel.uiState.folioInput)
        assertNull(viewModel.uiState.reporteEncontrado)
        assertNull(viewModel.uiState.error)
    }

    // --- PRUEBA 1.4: Búsqueda de folio exitosa y cálculo de nivel de progreso ---
    @Test
    fun `buscarReporte con folio existente actualiza reporteEncontrado y nivelProgreso`() {
        val folio = "RIETI-ECA-2026-00010"
        val mockResponse = ConsultaReporteWrapper(
            reporte = ConsultaReporteResponse(
                folio = folio,
                estatus = "En revisión",
                fechaRegistro = "2026-10-11T00:12:17.000Z",
                fechaUltimaAtencion = null,
                municipio = "Ecatepec de Morelos"
            )
        )

        coEvery { repository.consultarReportePorFolio(folio) } returns Response.success(mockResponse)

        viewModel.onFolioInputChange(folio)
        var callbackEjecutado = false

        viewModel.buscarReporte {
            callbackEjecutado = true
        }

        // Avanzar las corrutinas
        testDispatcher.scheduler.advanceUntilIdle()

        // Verificaciones
        assertTrue("El callback de éxito debió ejecutarse", callbackEjecutado)
        assertFalse("isLoading debe volver a false", viewModel.uiState.isLoading)
        assertEquals(mockResponse.reporte, viewModel.uiState.reporteEncontrado)
        assertEquals(2, viewModel.uiState.nivelProgreso) // "En revisión" -> Nivel 2
        assertNull(viewModel.uiState.error)
    }

    // --- PRUEBA 1.5: Búsqueda de folio no encontrado (Error 404 o bad request) ---
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
}