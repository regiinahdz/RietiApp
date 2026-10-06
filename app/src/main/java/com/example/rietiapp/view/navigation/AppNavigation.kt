package com.example.rietiapp.view.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rietiapp.view.screens.pantallaPrincipal.HomeScreen
//import com.example.rietiapp.view.screens.formularioPantallas.ConfirmacionScreen
//import com.example.rieti.view.screens.FormularioScreen
//import com.example.rieti.view.screens.PeligroScreen
//import com.example.rieti.view.components.RietiTopBar
//import com.example.rieti.view.screens.ConsultaReporteScreen
//import com.example.rieti.view.screens.SeguimientoScreen
//import com.example.rieti.view.screens.TipoReporteScreen
import com.example.rietiapp.view.components.RietiTopBar
import com.example.rietiapp.view.screens.formularioPantallas.ConfirmacionScreen
import com.example.rietiapp.view.screens.formularioPantallas.FormularioScreen
import com.example.rietiapp.view.screens.formularioPantallas.TipoReporteScreen
import com.example.rietiapp.view.screens.pantallaPrincipal.PeligroScreen
import com.example.rietiapp.view.screens.seguimientoReportes.ConsultaReporteScreen
import com.example.rietiapp.view.screens.seguimientoReportes.SeguimientoScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val currentRoute =
        navController.currentBackStackEntryAsState()
            .value?.destination?.route

    Scaffold(

        topBar = {

            RietiTopBar(
                mostrarAtras = currentRoute != Routes.HOME,
                onBackClick = {
                    navController.popBackStack()
                }
            )

        }

    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Routes.HOME) {
                HomeScreen(
                    onReportarClick = {
                        navController.navigate(Routes.TIPO_REPORTE)
                    },
                    onSeguimientoClick = {
                        navController.navigate(Routes.SEGUIMIENTO)
                    },
                ) {
                    navController.navigate(Routes.PELIGRO)
                }
            }

            composable(Routes.SEGUIMIENTO) {

                SeguimientoScreen(
                    onConsultarClick = {
                        navController.navigate(Routes.CONSULTA)
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

            composable(Routes.CONSULTA) {

                ConsultaReporteScreen(
                    folio = "RIETI-ATZ-2026-000123",
                    estado = "En revisión",
                    municipio = "Atizapán de Zaragoza"
                )
            }

            composable(Routes.CONFIRMACION) {

                ConfirmacionScreen(
                    onInicioClick = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.HOME) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(Routes.TIPO_REPORTE) {

                TipoReporteScreen(
                    onContinuarClick = {
                        navController.navigate(Routes.FORMULARIO)
                    }
                )
            }

            composable(Routes.FORMULARIO) {

                FormularioScreen(
                    onEnviarClick = {
                        navController.navigate(Routes.CONFIRMACION)
                    }
                )
            }

            composable(Routes.TOPBARRA) {
            }

            composable(Routes.CONSULTA) {
                ConsultaReporteScreen(
                    folio = "RIETI-ATZ-2026-000123",
                    estado = "En revisión",
                    municipio = "Atizapán de Zaragoza"
                )
            }


        }
    }
}