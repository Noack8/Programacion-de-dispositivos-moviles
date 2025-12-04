// Ubicación: com/example/proyecto1/Proyecto1App.kt

package com.example.proyecto1

import android.app.Application
import com.example.proyecto1.data.UserPreferences
import com.example.proyecto1.model.AppDatabase
import com.example.proyecto1.network.RetrofitClient
import com.example.proyecto1.repository.AuthRepository
import com.example.proyecto1.repository.TareaRepository

class Proyecto1App : Application() {
    // Inicialización "perezosa" (lazy) para que solo se cree cuando se necesite por primera vez.

    // 1. Base de datos (Room)
    private val database by lazy { AppDatabase.getInstance(this) }

    // 2. Cliente de Red (Retrofit)
// Se inicializa aquí para asegurar que el contexto esté disponible.
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.initialize(this)
    }

    // 3. Preferencias de Usuario (DataStore)
    private val userPreferences by lazy { UserPreferences(this) }

    // 4. Repositorio de Autenticación
    val authRepository by lazy { AuthRepository(RetrofitClient.instance, userPreferences) }

    // 5. Repositorio de Tareas
    val tareaRepository by lazy { TareaRepository(database.tareaDao(), RetrofitClient.instance) }
}
    