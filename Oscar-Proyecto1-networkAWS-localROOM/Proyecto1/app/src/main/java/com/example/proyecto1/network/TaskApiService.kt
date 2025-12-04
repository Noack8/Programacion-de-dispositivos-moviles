//Ubicación: com/example/proyecto1/network/TaskApiService.kt
package com.example.proyecto1.network

import com.example.proyecto1.model.AuthRequest
import com.example.proyecto1.model.AuthResponse
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.network.dto.SyncResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskApiService {
    @POST("login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @GET("tareas")
    suspend fun getTareas(): List<Tarea>

    @POST("tareas")
    suspend fun createTarea(@Body tarea: Tarea): Tarea

    // NUEVO: Endpoint para sincronizar un lote de cambios.
    // Envía una lista de tareas locales modificadas y recibe
    // una lista de tareas actualizadas y una lista de conflictos.
    @POST("tareas/sync")
    suspend fun syncTasks(@Body tasksToSync: List<Tarea>): SyncResponseDTO
}