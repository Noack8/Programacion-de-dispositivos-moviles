// Ubicación: com/example/proyecto1/network/RetrofitClient.kt

package com.example.proyecto1.network

import android.content.Context
import com.example.proyecto1.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.room.*
object RetrofitClient {
    private const val BASE_URL = "https://pruevasvercel.vercel.app/"

    // La única instancia de nuestra API. Será inicializada más tarde.
    lateinit var instance: TaskApiService
        private set // Para que solo se pueda modificar desde este archivo.

    // La única función que se debe llamar desde fuera para configurar todo.
    fun initialize(context: Context) {
        // Si ya está inicializado, no hacemos nada.
        if (::instance.isInitialized) {
            return
        }

        // 1. Creamos el interceptor de logging para espiar las llamadas.
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // 2. Creamos el interceptor de autenticación, usando el Contexto que recibimos.
        val authInterceptor = AuthInterceptor(context)

        // 3. Creamos el cliente OkHttp con AMBOS interceptores.
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)      // El que añade el token
            .addInterceptor(loggingInterceptor)   // El que nos permite espiar
            .build()

        // 4. Construimos la instancia de Retrofit con el cliente ya configurado.
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 5. Finalmente, creamos y asignamos la instancia de nuestro servicio API.
        instance = retrofit.create(TaskApiService::class.java)
    }
    // El interceptor se queda igual, ya está perfecto.
private class AuthInterceptor(private val context: Context) :
        Interceptor { override fun intercept(chain: Interceptor.Chain):
            Response { val userPreferences = UserPreferences(context)
        val token = runBlocking { userPreferences.getAuthToken().first() }
        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it") }
        return chain.proceed(requestBuilder.build())
        }
        }
}

