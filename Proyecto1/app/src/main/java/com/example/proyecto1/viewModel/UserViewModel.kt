package com.example.proyecto1.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class UserViewModel : ViewModel() {
    var nombre by mutableStateOf("")
    var boleta by mutableStateOf("")
    var correo by mutableStateOf("")
    var carrera by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    // Mensajes de error
    var nombreError by mutableStateOf<String?>(null)
    var boletaError by mutableStateOf<String?>(null)
    var correoError by mutableStateOf<String?>(null)
    var carreraError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var confirmPasswordError by mutableStateOf<String?>(null)

    var registroExitoso by mutableStateOf(false)

    fun validarCampos(): Boolean {
        var isValid = true

        nombreError = if (nombre.isBlank()) {
            isValid = false
            "El nombre es obligatorio"
        } else null

        boletaError = if (boleta.isBlank()) {
            isValid = false
            "La boleta es obligatoria"
        } else null

        correoError = if (!isEmailValido(correo)) {
            isValid = false
            "Correo no válido"
        } else null

        carreraError = if (carrera.isBlank()) {
            isValid = false
            "La carrera es obligatoria"
        } else null

        passwordError = if (password.length < 6) {
            isValid = false
            "La contraseña debe tener al menos 6 caracteres"
        } else null

        confirmPasswordError = if (confirmPassword != password) {
            isValid = false
            "Las contraseñas no coinciden"
        } else null

        registroExitoso = isValid
        return isValid
    }

    private fun isEmailValido(email: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        )
        return pattern.matcher(email).matches()
    }
}