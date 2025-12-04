//ubicacion; com/example/proyecto1/model/AuthData.kt
package com.example.proyecto1.model

// Modelo para ENVIAR en la petición de login/registro
data class AuthRequest(
    val email: String,
    val password: String
)

// Modelo para RECIBIR la respuesta de la API
data class AuthResponse(
    val token: String
)