//Ubicación: com/example/proyecto1/repository/AuthRepository.kt
package com.example.proyecto1.repository

import com.example.proyecto1.data.UserPreferences
import com.example.proyecto1.model.AuthRequest
import com.example.proyecto1.network.RetrofitClient
import com.example.proyecto1.network.TaskApiService

// CORRECCIÓN: El constructor debe aceptar los dos parámetros que le pasamos
class AuthRepository(
    private val apiService: TaskApiService, private val userPreferences: UserPreferences ) {
    // Asegurémonos que las funciones que usa el ViewModel estén aquí
    suspend fun login(email: String, pass: String) =
        apiService.login(AuthRequest(email, pass))

    suspend fun saveToken(token: String) {
        userPreferences.saveAuthToken(token)
    }

    fun getToken() = userPreferences.getAuthToken()
}