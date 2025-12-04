////Ubicación: com/example/proyecto1/apiclient/TaskApiService.kt
package com.example.proyecto1.apiclient

import com.example.proyecto1.model.Tarea // ¡IMPORTA TU CLASE TAREA AQUÍ!
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskApiService {

    @GET("tareas") // Asume que tu endpoint de la API es "/tareas"
    suspend fun getTasks(): List<Tarea>

    @POST("tareas")
    suspend fun createTask(@Body tarea: Tarea): Tarea

    @PUT("tareas/{id}")
    suspend fun updateTask(@Path("id") id: Int, @Body tarea: Tarea): Tarea

    @DELETE("tareas/{id}")
    suspend fun deleteTask(@Path("id") id: Int)
}
    