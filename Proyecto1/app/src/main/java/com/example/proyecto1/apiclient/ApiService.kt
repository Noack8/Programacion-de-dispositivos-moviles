package com.example.proyecto1.apiclient

import com.example.proyecto1.model.Tarea
import com.example.proyecto1.model.TareaCreateRequest
import com.example.proyecto1.model.Usuario
import com.example.proyecto1.viewModel.LoginData
import com.example.proyecto1.viewModel.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // GET con parámetro de query
    @GET("tareas")
    suspend fun getTasks(@Query("usuario_id") usuarioId: Int): List<Tarea>

    @POST("tareas")
    suspend fun createTask(@Body task: TareaCreateRequest): Tarea

    @DELETE("tareas/{id}")
    suspend fun deleteTask(@Path("id") id: Int?, @Query("usuario_id") usuarioId: Int)

    @PUT("tareas/{id}")
    suspend fun updateTask(@Path("id") id: Int?, @Body task: Tarea)

    // Login endpoint - AÑADIDO
    @POST("login")
    suspend fun login(@Body loginData: LoginData): Response<LoginResponse>
    @POST("usuarios")
    suspend fun register(@Body usuario: Usuario): Response<Usuario>
}