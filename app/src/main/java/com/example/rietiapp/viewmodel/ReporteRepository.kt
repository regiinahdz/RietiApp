package com.example.rietiapp.viewmodel

import com.example.rietiapp.model.api.RetrofitInstance
import com.example.rietiapp.model.datos.ReporteRequest

class ReporteRepository {

    suspend fun crearReporte(
        request: ReporteRequest
    ) =
        RetrofitInstance.api.crearReporte(
            request
        )
}