package com.example.rietiapp.model.datos

import com.google.gson.annotations.SerializedName

data class Municipio(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("nombre")
    val nombre: String = ""
)