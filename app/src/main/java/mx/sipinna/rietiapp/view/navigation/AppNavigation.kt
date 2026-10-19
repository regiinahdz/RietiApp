/**
 * Grafo de navegación central de la aplicación RIETI.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
package mx.sipinna.rietiapp.view.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mx.sipinna.rietiapp.view.screens.formularioPantallas.ConfirmacionScreen
import mx.sipinna.rietiapp.view.screens.formularioPantallas.FormularioScreen
import mx.sipinna.rietiapp.view.screens.formularioPantallas.TipoReporteScreen
import mx.sipinna.rietiapp.view.screens.pantallaPrincipal.HomeScreen
import mx.sipinna.rietiapp.view.screens.pantallaPrincipal.PeligroScreen
import mx.sipinna.rietiapp.view.screens.seguimientoReportes.ConsultaReporteScreen
import mx.sipinna.rietiapp.view.screens.seguimientoReportes.SeguimientoScreen
import mx.sipinna.rietiapp.viewmodel.ReporteViewModel
import mx.sipinna.rietiapp.viewmodel.SeguimientoViewModel

/**
 * Define la estructura de rutas [NavHost] y sus transiciones de pantalla.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val reporteViewModel: ReporteViewModel = viewModel()
    val seguimientoViewModel: SeguimientoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = Modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onReportarClick = {
                    reporteViewModel.updateState { mx.sipinna.rietiapp.model.datos.ReporteUiState() }
                    navController.navigate(Routes.TIPO_REPORTE)
                },
                onSeguimientoClick = {
                    navController.navigate(Routes.SEGUIMIENTO)
                }
            )
        }

        composable(Routes.TIPO_REPORTE) {
            TipoReporteScreen(
                viewModel = reporteViewModel,
                onContinuarClick = {
                    navController.navigate(Routes.FORMULARIO)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.FORMULARIO) {
            FormularioScreen(
                viewModel = reporteViewModel,
                onEnviarClick = {
                    navController.navigate(Routes.CONFIRMACION) {
                        popUpTo(Routes.HOME)
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.CONFIRMACION) {
            ConfirmacionScreen(
                viewModel = reporteViewModel,
                onInicioClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.SEGUIMIENTO) {
            SeguimientoScreen(
                viewModel = seguimientoViewModel,
                onConsultarClick = {
                    navController.navigate(Routes.CONSULTA)
                },
                onBackClick = {
                    seguimientoViewModel.limpiarEstado()
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.CONSULTA) {
            ConsultaReporteScreen(
                viewModel = seguimientoViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.PELIGRO) {
            PeligroScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
