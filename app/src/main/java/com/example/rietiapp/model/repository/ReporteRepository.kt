package com.example.rietiapp.model.repository

import com.example.rietiapp.model.api.RetrofitInstance
import com.example.rietiapp.model.datos.ReporteRequest

class ReporteRepository {

    suspend fun crearReporte(
        reporte: ReporteRequest
    ) =
        RetrofitInstance.api.crearReporte(
            reporte
        )
}