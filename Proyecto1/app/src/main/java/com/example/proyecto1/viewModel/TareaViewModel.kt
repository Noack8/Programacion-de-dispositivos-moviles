package com.example.proyecto1.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto1.apiclient.RetrofitClient
import com.example.proyecto1.model.Tarea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.plus

class TareaViewModel : ViewModel() {
    // Propiedades para los campos de entrada
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var dateAssigned by mutableStateOf("")
    var timeAssigned by mutableStateOf("")
    var deadlineDate by mutableStateOf("")
    var deadlineTime by mutableStateOf("")
    var completed by mutableStateOf(false)
    var priority by mutableStateOf(1)
    // Mensajes de error
    var nameError by mutableStateOf<String?>(null)
    var descriptionError by mutableStateOf<String?>(null)
    var dateAssignedError by mutableStateOf<String?>(null)
    var timeAssignedError by mutableStateOf<String?>(null)
    var deadlineDateError by mutableStateOf<String?>(null)
    var deadlineTimeError by mutableStateOf<String?>(null)
    var priorityError by mutableStateOf<String?>(null)
    var registroExitoso by mutableStateOf(false)
    fun validarCampos(): Boolean {
        var isValid = true

        nameError = if (name.isBlank()) {
            isValid = false
            "El nombre es obligatorio"
        } else null

        descriptionError = if (description.isBlank()) {
            isValid = false
            "Agregar una descripcion a su tarea"
        } else null

        dateAssignedError = if (dateAssigned.isBlank()) {
            isValid = false
            "Fecha de asignacion no valida"
        } else null

        timeAssignedError = if (timeAssigned.isBlank()) {
            isValid = false
            "Hora de asignacion no valida"
        } else null

        deadlineDateError = if (deadlineDate.isBlank()) {
            isValid = false
            "Fecha limite no valida"
        } else null

        deadlineTimeError = if (deadlineTime.isBlank()) {
            isValid = false
            "Hora limite no valida"
        } else null

        priorityError = if (priority.toInt() !in 1..10) {
            isValid = false
            "La prioridad debe ser un número del 1 al 10"
        } else null

        registroExitoso = isValid
        return isValid
    }
    fun limpiarCampos() {
        name = ""
        description = ""
        dateAssigned = ""
        timeAssigned = ""
        deadlineDate = ""
        deadlineTime = ""
        completed = false
        priority = 1
        // Limpiar errores
        nameError = null
        descriptionError = null
        dateAssignedError = null
        timeAssignedError = null
        deadlineDateError = null
        deadlineTimeError = null
        priorityError = null
        registroExitoso = false
    }
    private val _tasks = MutableStateFlow<List<Tarea>>(emptyList())
    val tasks: StateFlow<List<Tarea>> = _tasks

    fun getTasks() {
        viewModelScope.launch {
            try {
                val result = RetrofitClient.api.getTasks()
                _tasks.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addTask(task: Tarea) {
        viewModelScope.launch {
            try {
                val newTask = RetrofitClient.api.createTask(task)
                _tasks.value = _tasks.value + newTask
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteTask(task: Tarea) {
        viewModelScope.launch {
            try {
                RetrofitClient.api.deleteTask(task.id)
                _tasks.value = _tasks.value - task
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun cambiarCompletada(task: Tarea, estado: Boolean) {
        viewModelScope.launch {
            try {
                val updatedTask = task.copy(completed = estado)
                RetrofitClient.api.updateTask(task.id,updatedTask)
                _tasks.value = _tasks.value.map { if (it.id == task.id) updatedTask else it }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _selectedTarea = MutableStateFlow<Tarea?>(null)
    val selectedTarea: StateFlow<Tarea?> get() = _selectedTarea

    fun seleccionarTareaParaEditar(task: Tarea) {
        _selectedTarea.value = task
        name = task.name
        description = task.description ?: ""
        dateAssigned = task.dateAssigned?.substringBefore("T") ?: ""
        timeAssigned = task.timeAssigned ?: ""
        deadlineDate = task.deadlineDate?.substringBefore("T") ?: ""
        deadlineTime = task.deadlineTime ?: ""
        completed = task.completed
        priority = task.priority
    }
    fun actualizarTask(task: Tarea) {
        viewModelScope.launch {
            try {
                RetrofitClient.api.updateTask(task.id, task)
                _tasks.value = _tasks.value.map { if (it.id == task.id) task else it }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}