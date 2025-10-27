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

    var titulo by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var fechaAsignada by mutableStateOf("")
    var fechaLimite by mutableStateOf("")
    var completado by mutableStateOf(false)
    var prioridad by mutableStateOf("1")
    // Mensajes de error
    var tituloError by mutableStateOf<String?>(null)
    var descripcionError by mutableStateOf<String?>(null)
    var fechaAsignadaError by mutableStateOf<String?>(null)
    var fechaLimiteError by mutableStateOf<String?>(null)
    var prioridadError by mutableStateOf<String?>(null)
    var registroExitoso by mutableStateOf(false)
    fun validarCampos(): Boolean {
        var isValid = true

        tituloError = if (titulo.isBlank()) {
            isValid = false
            "El nombre es obligatorio"
        } else null

        descripcionError = if (descripcion.isBlank()) {
            isValid = false
            "Agregar una descripcion a su tarea"
        } else null

        fechaLimiteError = if (!isFechaValido(fechaLimite)) {
            isValid = false
            "Fecha limite no valida"
        } else null

        fechaAsignadaError = if (!isFechaValido(fechaAsignada)) {
            isValid = false
            "Fecha de asignacion no valida"
        } else null

        prioridadError = if (prioridad.toIntOrNull() == null || prioridad.toInt() !in 1..10) {
            isValid = false
            "La prioridad debe ser un número del 1 al 10"
        } else null

        registroExitoso = isValid
        return isValid
    }
    private fun isFechaValido(fecha: String): Boolean {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formato.isLenient = false
        return try {
            val fechaParseada = formato.parse(fecha)
            true
        } catch (e: Exception) {
            false
        }
    }
    fun limpiarCampos() {
        titulo = ""
        descripcion = ""
        fechaAsignada = ""
        fechaLimite = ""
        completado = false
        prioridad = "1"
        // Limpiar errores
        tituloError = null
        descripcionError = null
        fechaAsignadaError = null
        fechaLimiteError = null
        prioridadError = null
        registroExitoso = false
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
    fun borrarTarea(tarea: Tarea) {
        viewModelScope.launch {
            repository.eliminarTarea(tarea)
        }
    }
    fun cambiarCompletada(tarea: Tarea, estado: Boolean) {
        viewModelScope.launch {
            repository.modificarCompletada(tarea.id, estado)
            loadTareas()
        }
    }
    fun seleccionarTareaParaEditar(tarea: Tarea) {
        _selectedTarea.value = tarea
        titulo = tarea.titulo
        descripcion = tarea.descripcion
        fechaAsignada = tarea.fechaAsignada
        fechaLimite = tarea.fechaLimite
        completado = tarea.completado
        prioridad = tarea.prioridad.toString()
    }
    fun actualizarTarea(tarea: Tarea) {
        viewModelScope.launch {
            repository.actualizarTarea(tarea)
            loadTareas()
        }
    }
}