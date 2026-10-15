package com.example.rietiapp.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente Singleton de Retrofit para configurar las llamadas de red a la API de RIETI.
 *
 * @author Regina Hdz Moreno
 * @author Ricardo Oved Cornejo Castro
 */
object RetrofitInstance {

    private const val BASE_URL =
        "https://csyacibpg4mwuom4vwqyem4bie0asjsc.lambda-url.us-east-1.on.aws/"

    /**
     * Instancia perezosa (lazy) de [ReporteApi].
     */
    val api: ReporteApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReporteApi::class.java)
    }
}
