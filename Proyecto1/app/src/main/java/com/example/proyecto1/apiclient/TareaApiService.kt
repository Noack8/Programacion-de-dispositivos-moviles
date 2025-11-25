package com.example.proyecto1.apiclient

import com.example.proyecto1.model.Tarea
import retrofit2.http.*
interface TareaApiService {
    @GET("tareas")
    suspend fun getTasks(): List<Tarea>
    @POST("tareas")
    suspend fun createTask(@Body task: Tarea): Tarea
    @DELETE("tareas/{id}")
    suspend fun deleteTask(@Path("id") id: Int?)
    @PUT("tareas/{id}")
    suspend fun updateTask(@Path("id") id: Int?, @Body task: Tarea)
}