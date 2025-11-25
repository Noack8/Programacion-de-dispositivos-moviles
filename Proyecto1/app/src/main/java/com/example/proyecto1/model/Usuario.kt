package com.example.proyecto1.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    val id: Int? = null,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("correo") val correo: String?,
    @SerializedName("boleta") val boleta: String?,
    @SerializedName("carrera") val carrera: String?,
    @SerializedName("password") val password: String?,
    val token: String? = null
)
