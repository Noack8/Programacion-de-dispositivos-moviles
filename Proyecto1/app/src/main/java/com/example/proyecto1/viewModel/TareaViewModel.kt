package com.example.proyecto1.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.model.TareaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

class TareaViewModel(private val repository: TareaRepository) : ViewModel() {

    var nombre by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var fechaAsignada by mutableStateOf("")
    var fechaLimite by mutableStateOf("")
    var completado by mutableStateOf(false)
    var prioridad by mutableStateOf("")
    // Mensajes de error
    var nombreError by mutableStateOf<String?>(null)
    var descripcionError by mutableStateOf<String?>(null)
    var fechaAsignadaError by mutableStateOf<String?>(null)
    var fechaLimiteError by mutableStateOf<String?>(null)
    var prioridadError by mutableStateOf<String?>(null)
    var registroExitoso by mutableStateOf(false)
    fun validarCampos(): Boolean {
        var isValid = true

        nombreError = if (nombre.isBlank()) {
            isValid = false
            "El nombre es obligatorio"
        } else null

        descripcionError = if (descripcion.isBlank()) {
            isValid = false
            "Agrege una descripcion para que no se le olvide de que va la tarea"
        } else null

        fechaLimiteError = if (!isFechaValido(fechaLimite)) {
            isValid = false
            "Correo no válido"
        } else null

        fechaAsignadaError = if (!isFechaValido(fechaAsignada)) {
            isValid = false
            "Correo no válido"
        } else null

        prioridadError = if (prioridad.isBlank()) {
            isValid = false
            "La boleta es obligatoria"
        } else null

        registroExitoso = isValid
        return isValid
    }
    private fun isFechaValido(fecha: String): Boolean {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formato.isLenient = false // Importante para que no acepte fechas como 32/01/2023
        return try {
            val fechaParseada = formato.parse(fecha)
            true
        } catch (e: Exception) {
            false
        }
    }
    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareas: StateFlow<List<Tarea>> get() = _tareas

    private val _selectedTarea = MutableStateFlow<Tarea?>(null)
    val selectedTarea: StateFlow<Tarea?> get() = _selectedTarea
    fun loadTareas() {
        viewModelScope.launch {
            _tareas.value = repository.getAll()
        }
    }
    fun addTarea(tarea: Tarea) {
        viewModelScope.launch {
            val success = repository.insert(tarea)
            if (!success) {
                Log.e("PruebasJuan", "Error al insertar usuario")
            }
        }
    }
    /*
    fun findUserByEmail(email: String) {
        viewModelScope.launch {
            _selectedTarea.value = repository.findByEmail(email)
        }
    }

    fun findUserByName(name: String) {
        viewModelScope.launch {
            _selectedUser.value = repository.findByName(name)
        }
    }

    fun findUserByStudentId(studentId: String) {
        viewModelScope.launch {
            _selectedUser.value = repository.findByStudentId(studentId)
        }
    }
    */
}