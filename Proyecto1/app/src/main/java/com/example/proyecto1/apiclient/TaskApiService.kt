package com.example.proyecto1.apiclient

import com.example.proyecto1.model.Task
import retrofit2.http.*
interface TaskApiService {
    @GET("tareas")
    suspend fun getTasks(): List<Task>
    @POST("tareas")
    suspend fun createTask(@Body task: Task): Task
    @DELETE("tareas/{id}")
    suspend fun deleteTask(@Path("id") id: Int?)
    @PUT("tareas/{id}")
    suspend fun updateTask(@Path("id") id: Int?, @Body task: Task)
}