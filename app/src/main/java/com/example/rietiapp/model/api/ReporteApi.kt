package com.example.rietiapp.model.api

import com.example.rietiapp.model.datos.ReporteRequest
import com.example.rietiapp.model.datos.ReporteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReporteApi {

    @POST("api/v1/reportes")
    suspend fun crearReporte(
        @Body reporte: ReporteRequest
    ): Response<ReporteResponse>
}