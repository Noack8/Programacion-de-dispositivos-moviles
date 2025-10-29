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
import java.util.Calendar // Correct import
import java.util.Date
import java.util.Locale

class TareaViewModel(private val repository: TareaRepository) : ViewModel() {

    // --- ESTADOS (sin cambios) ---
    var titulo by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var fechaAsignada by mutableStateOf("")
    var fechaLimite by mutableStateOf("")
    var horaAsignada by mutableStateOf("")
    var horaLimite by mutableStateOf("")
    var completado by mutableStateOf(false)
    var prioridad by mutableStateOf("1")

    // --- MENSAJES DE ERROR (sin cambios) ---
    var tituloError by mutableStateOf<String?>(null)
    var descripcionError by mutableStateOf<String?>(null)
    var fechaAsignadaError by mutableStateOf<String?>(null)
    var fechaLimiteError by mutableStateOf<String?>(null)
    var horaAsignadaError by mutableStateOf<String?>(null)
    var horaLimiteError by mutableStateOf<String?>(null)
    var prioridadError by mutableStateOf<String?>(null)
    var registroExitoso by mutableStateOf(false)

    // --- FUNCIÓN DE UTILIDAD (sin cambios) ---
    private fun parseFechaHora(fechaStr: String, horaStr: String): Date? {
        if (fechaStr.isBlank() || horaStr.isBlank()) return null
        val formatoCompleto = SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.getDefault())
        formatoCompleto.isLenient = false
        return try {
            formatoCompleto.parse("$fechaStr $horaStr")
        } catch (e: Exception) {
            null
        }
    }

    // ===================================================================
    // --- INICIO DE LA CORRECCIÓN: validarCampos() REESTRUCTURADA ---
    // ===================================================================
    fun validarCampos(): Boolean {
        // 1. Limpiar todos los errores al inicio de la validación
        tituloError = null
        descripcionError = null
        fechaAsignadaError = null
        fechaLimiteError = null
        horaAsignadaError = null
        horaLimiteError = null
        prioridadError = null

        var isValid = true

        // 2. Validaciones simples (campos vacíos, etc.)
        if (titulo.isBlank()) {
            tituloError = "El título es obligatorio"
            isValid = false
        }
        if (descripcion.isBlank()) {
            descripcionError = "La descripción es obligatoria"
            isValid = false
        }
        if (prioridad.toIntOrNull() == null || prioridad.toInt() !in 1..10) {
            prioridadError = "La prioridad debe ser un número del 1 al 10"
            isValid = false
        }

        // 3. Validación de Fecha y Hora
        val fechaHoraAsignada = parseFechaHora(fechaAsignada, horaAsignada)
        val fechaHoraLimite = parseFechaHora(fechaLimite, horaLimite)

        // 3.1. Validar que los campos de fecha/hora no estén vacíos y tengan formato correcto
        if (fechaAsignada.isBlank()) {
            fechaAsignadaError = "Seleccione una fecha"
            isValid = false
        }
        if (horaAsignada.isBlank()) {
            horaAsignadaError = "Seleccione una hora"
            isValid = false
        }
        if (fechaLimite.isBlank()) {
            fechaLimiteError = "Seleccione una fecha"
            isValid = false
        }
        if (horaLimite.isBlank()) {
            horaLimiteError = "Seleccione una hora"
            isValid = false
        }

        // 3.2. Si los campos están llenos, proceder a la validación lógica
        if (fechaHoraAsignada != null && fechaHoraLimite != null) {
            val hoy = Calendar.getInstance().apply {
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.time

            // 3.2.1. La fecha de asignación no puede ser anterior a hoy
            if (fechaHoraAsignada.before(hoy)) {
                // Asignamos el error a ambos campos para que el usuario sepa que la combinación es incorrecta
                fechaAsignadaError = "La asignación no puede ser en el pasado"
                horaAsignadaError = " " // Un espacio para que el campo se marque en rojo sin texto
                isValid = false
            }

            // 3.2.2. La fecha límite no puede ser anterior a la de asignación
            if (fechaHoraLimite.before(fechaHoraAsignada)) {
                // Asignamos el error a ambos campos de fecha/hora límite
                fechaLimiteError = "La fecha límite es inválida"
                horaLimiteError = "No puede ser antes de la asignación"
                isValid = false
            }
        }

        registroExitoso = isValid
        return isValid
    }
    // ===================================================================
    // --- FIN DE LA CORRECCIÓN ---
    // ===================================================================

    // La función isFechaValido ya no es necesaria, puedes borrarla si quieres.
    // private fun isFechaValido(fecha: String): Boolean { ... }

    fun limpiarCampos() {
        titulo = ""
        descripcion = ""
        fechaAsignada = ""
        fechaLimite = ""
        horaAsignada = ""
        horaLimite = ""
        completado = false
        prioridad = "1"
        // Limpiar errores
        tituloError = null
        descripcionError = null
        fechaAsignadaError = null
        fechaLimiteError = null
        horaAsignadaError = null
        horaLimiteError = null
        prioridadError = null
        registroExitoso = false
    }

    // --- El resto de las funciones (loadTareas, addTarea, etc.) permanecen igual ---

    private val _tareas = MutableStateFlow<List<Tarea>>(emptyList())
    val tareas: StateFlow<List<Tarea>> get() = _tareas

    private val _selectedTarea = MutableStateFlow<Tarea?>(null)
    val selectedTarea: StateFlow<Tarea?> get() = _selectedTarea
    fun loadTareas() {
        viewModelScope.launch {
            repository.getAll()?.let { _tareas.value = it }
        }
    }
    fun addTarea(tarea: Tarea) {
        viewModelScope.launch {
            repository.insert(tarea)
            loadTareas()
        }
    }
    fun borrarTarea(tarea: Tarea) {
        viewModelScope.launch {
            repository.eliminarTarea(tarea)
            loadTareas()
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
        horaAsignada = tarea.horaAsignada
        horaLimite = tarea.horaLimite
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
