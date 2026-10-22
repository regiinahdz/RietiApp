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

    private val mockViewModel: ReporteViewModel = mockk(relaxed = true)

    // --- TC-05 (Soporte TC-03): Modalidad Anónima ---
    @Test
    fun confirmacionScreen_TC03_TC05_reporteAnonimo_muestraCardAnonimoYOcultaCorreo() {
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

        composeTestRule.onNodeWithText("MODALIDAD ANÓNIMA").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "Tu reporte ha sido registrado de forma confidencial. No se solicitaron ni guardaron datos personales de contacto."
        ).assertIsDisplayed()

        composeTestRule.onNodeWithText("FOLIO DE SEGUIMIENTO").assertDoesNotExist()
    }

    // --- TC-06 (Soporte TC-04): Modalidad Seguimiento ---
    @Test
    fun confirmacionScreen_TC04_TC06_reporteSeguimiento_muestraFolioYCorreo() {
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

        composeTestRule.onNodeWithText("RIETI-ECA-2026-00011").assertIsDisplayed()
        composeTestRule.onNodeWithText(
            "Enviaremos actualizaciones sobre el seguimiento de este reporte al correo registrado: ciudadano@edomex.gob.mx",
            substring = true
        ).assertIsDisplayed()

        composeTestRule.onNodeWithText("Copiar").assertIsDisplayed()
        composeTestRule.onNodeWithText("Compartir").assertIsDisplayed()
    }
}