package com.example.proyecto1.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto1.apiclient.RetrofitClient
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    // Propiedades para los campos de entrada
    var correo by mutableStateOf("")
    var password by mutableStateOf("")

    // Estados de la UI
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    // Mensajes de error de validación
    var correoError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    // Datos del usuario logueado
    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData

    // Token de autenticación
    var token by mutableStateOf("")
    var userId by mutableStateOf(-1)

    fun validarCampos(): Boolean {
        var isValid = true

        correoError = if (correo.isBlank()) {
            isValid = false
            "El correo es obligatorio"
        } else null

        passwordError = if (password.isBlank()) {
            isValid = false
            "La contraseña es obligatoria"
        } else null

        return isValid
    }

    fun login() {
        if (!validarCampos()) {
            Log.d("JuanPruebas","❌ Validación de campos falló")
            return
        }

        isLoading = true
        errorMessage = null

        Log.d("JuanPruebas","🔄 Iniciando login para: ${correo}")

        viewModelScope.launch {
            try {
                val loginData = LoginData(correo, password)
                Log.d("JuanPruebas","📤 Enviando request de login...")

                val response = RetrofitClient.api.login(loginData)
                Log.d("JuanPruebas","📥 Respuesta recibida: ${response.code()}")

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("JuanPruebas","✅ Login exitoso: $loginResponse")

                    if (loginResponse != null) {
                        // Guardar los datos importantes
                        token = loginResponse.token
                        userId = loginResponse.id

                        _userData.value = UserData(
                            id = loginResponse.id,
                            nombre = loginResponse.nombre,
                            token = loginResponse.token
                        )

                        // Limpiar campos
                        correo = ""
                        password = ""
                        errorMessage = null

                        Log.d("JuanPruebas","🔐 Token guardado: $token")
                        Log.d("JuanPruebas","👤 User ID: $userId")
                    } else {
                        errorMessage = "Error en la respuesta del servidor"
                        Log.e("JuanPruebas","❌ Respuesta body es null")
                    }
                } else {
                    errorMessage = when (response.code()) {
                        401 -> "Credenciales incorrectas"
                        500 -> "Error del servidor"
                        else -> "Error al iniciar sesión: ${response.code()}"
                    }
                    Log.d("JuanPruebas","❌ Error en login: $errorMessage")
                }
            } catch (e: Exception) {
                errorMessage = "Error de conexión: ${e.message}"
                Log.e("JuanPruebas","💥 Excepción en login: ${e.message}")
                e.printStackTrace()
            } finally {
                isLoading = false
                Log.d("JuanPruebas","🏁 Login finalizado, isLoading: $isLoading")
            }
        }
    }

    fun logout() {
        _userData.value = null
        token = ""
        userId = -1
        correo = ""
        password = ""
        errorMessage = null
    }
}

data class UserData(
    val id: Int,
    val nombre: String,
    val token: String
)

data class LoginData(
    @SerializedName("correo") val correo: String,
    @SerializedName("password") val password: String
)

data class LoginResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("token") val token: String
)