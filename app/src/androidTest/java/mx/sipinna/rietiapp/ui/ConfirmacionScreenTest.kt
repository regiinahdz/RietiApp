package mx.sipinna.rietiapp.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import io.mockk.every
import io.mockk.mockk
import mx.sipinna.rietiapp.model.datos.ReporteUiState
import mx.sipinna.rietiapp.view.screens.formularioPantallas.ConfirmacionScreen
import mx.sipinna.rietiapp.viewmodel.ReporteViewModel
import org.junit.Rule
import org.junit.Test

class ConfirmacionScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Mock del ViewModel
    private val mockViewModel: ReporteViewModel = mockk(relaxed = true)

    // --- PRUEBA 2.1: Visualización de Modalidad Anónima ---
    @Test
    fun confirmacionScreen_reporteAnonimo_muestraCardAnonimoYOcultaCorreo() {
        val uiStateAnonimo = ReporteUiState(
            folio = "RIETI-ECA-2026-00010",
            modalidad = "ANONIMO",
            correo = ""
        )
        every { mockViewModel.uiState } returns uiStateAnonimo

        composeTestRule.setContent {
            ConfirmacionScreen(
                viewModel = mockViewModel,
                onInicioClick = {}
            )
        }

        // Validar que se muestre la tarjeta con el aviso de Modalidad Anónima
        composeTestRule.onNodeWithText("MODALIDAD ANÓNIMA").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "Tu reporte ha sido registrado de forma confidencial. No se solicitaron ni guardaron datos personales de contacto."
        ).assertIsDisplayed()

        // Validar que la sección de Folio de Seguimiento no aparezca en modalidad anónima
        composeTestRule.onNodeWithText("FOLIO DE SEGUIMIENTO").assertDoesNotExist()
    }

    // --- PRUEBA 2.2: Visualización de Folio y Correo en Modalidad Seguimiento ---
    @Test
    fun confirmacionScreen_reporteSeguimiento_muestraFolioYCorreo() {
        val uiStateSeguimiento = ReporteUiState(
            folio = "RIETI-ECA-2026-00011",
            modalidad = "SEGUIMIENTO",
            correo = "ciudadano@edomex.gob.mx"
        )
        every { mockViewModel.uiState } returns uiStateSeguimiento

        composeTestRule.setContent {
            ConfirmacionScreen(
                viewModel = mockViewModel,
                onInicioClick = {}
            )
        }

        // Validar que el Folio asignado se muestre en pantalla
        composeTestRule.onNodeWithText("RIETI-ECA-2026-00011").assertIsDisplayed()

        // Validar que el aviso con el correo de contacto se muestre adecuadamente
        composeTestRule.onNodeWithText(
            "Enviaremos actualizaciones sobre el seguimiento de este reporte al correo registrado: ciudadano@edomex.gob.mx",
            substring = true
        ).assertIsDisplayed()

        // Validar la presencia de los botones de acción
        composeTestRule.onNodeWithText("Copiar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Compartir").assertIsDisplayed()
    }
}