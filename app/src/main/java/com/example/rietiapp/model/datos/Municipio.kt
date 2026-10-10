package com.example.rietiapp.model.datos

import com.google.gson.annotations.SerializedName

data class Municipio(
    @SerializedName("idMunicipio")
    val id: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("siglas")
    val siglas: String? = null,

    @SerializedName("clave_inegi")
    val claveInegi: String? = null,

    @SerializedName("adherido_rieti")
    val adheridoRieti: Int = 0
)