package mx.sipinna.rietiapp.ui

import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import mx.sipinna.rietiapp.view.navigation.Routes
import mx.sipinna.rietiapp.view.screens.pantallaPrincipal.HomeScreen
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavHostTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupNavHost() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext()).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeTestRule.setContent {
            NavHost(
                navController = navController,
                startDestination = Routes.HOME
            ) {
                composable(Routes.HOME) {
                    HomeScreen(
                        onReportarClick = { navController.navigate(Routes.TIPO_REPORTE) },
                        onSeguimientoClick = { navController.navigate(Routes.SEGUIMIENTO) }
                    )
                }
                composable(Routes.TIPO_REPORTE) {
                    Text("Pantalla Tipo Reporte")
                }
                composable(Routes.SEGUIMIENTO) {
                    Text("Pantalla Seguimiento")
                }
            }
        }
    }

    @Test
    fun navHost_inicio_muestraPantallaInicial() {
        composeTestRule.waitForIdle()
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Routes.HOME, currentRoute)
    }

    @Test
    fun navHost_clickNavegarATipoReporte_cambiaDeRutaCorrectamente() {
        composeTestRule.waitForIdle()

        // Usa el texto único de la primera tarjeta: "Reportar caso"
        composeTestRule
            .onNodeWithText("Reportar caso", substring = true, ignoreCase = true)
            .performClick()

        composeTestRule.waitForIdle()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Routes.TIPO_REPORTE, currentRoute)
    }

    @Test
    fun navHost_clickNavegarASeguimiento_cambiaDeRutaCorrectamente() {
        composeTestRule.waitForIdle()

        // Usa el texto único de la segunda tarjeta: "Dar seguimiento"
        composeTestRule
            .onNodeWithText("Dar seguimiento", substring = true, ignoreCase = true)
            .performClick()

        composeTestRule.waitForIdle()

        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Routes.SEGUIMIENTO, currentRoute)
    }
}