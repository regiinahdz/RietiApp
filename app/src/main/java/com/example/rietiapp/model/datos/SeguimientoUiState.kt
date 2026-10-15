package com.example.rietiapp.model.datos

/**
 * Estado UI inmutable para la pantalla de consulta y seguimiento de reportes por folio.
 *
 * @property folioInput Texto ingresado por el usuario en el campo de búsqueda.
 * @property isLoading Indicador de estado de carga mientras se consulta la API.
 * @property reporteEncontrado Datos del reporte recuperados [ConsultaReporteResponse].
 * @property nivelProgreso Nivel numérico del avance del reporte (de 1 a 5).
 * @property descripcionEstatus Explicación detallada del estado actual.
 * @property error Mensaje de error a desplegar si el folio no se encuentra o falla la red.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
data class SeguimientoUiState(
    val folioInput: String = "",
    val isLoading: Boolean = false,
    val reporteEncontrado: ConsultaReporteResponse? = null,
    val nivelProgreso: Int = 1,
    val descripcionEstatus: String = "",
    val error: String? = null
)
