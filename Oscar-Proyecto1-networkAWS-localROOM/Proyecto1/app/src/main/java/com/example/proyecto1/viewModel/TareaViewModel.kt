//Ubicación: com/example/proyecto1/viewModel/TareaViewModel.kt
package com.example.proyecto1.viewModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.repository.TareaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TareaViewModel(private val repository: TareaRepository) : ViewModel() {
    // La UI observa directamente el Flow del repositorio.
    val tareas: StateFlow<List<Tarea>> = repository.getTareas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun crearTarea(nombre: String, descripcion: String) {
        Log.d("TAREA_DEBUG", "ViewModel.crearTarea: Función ejecutada. Lanzando corrutina.")
        viewModelScope.launch {
            // AÑADIMOS UN BLOQUE try-catch PARA ATRAPAR CUALQUIER ERROR
            try {
                Log.d("TAREA_DEBUG", "ViewModel.crearTarea: DENTRO DE LA CORRUTINA (try block). Llamando a repository.createTarea...")

                repository.createTarea(nombre, descripcion)

                Log.d("TAREA_DEBUG", "ViewModel.crearTarea: repository.createTarea TERMINÓ. Llamando a sincronizar()...")
                sincronizar()

            } catch (e: Exception) {
                // SI ALGO FALLA, ESTE BLOQUE SE EJECUTARÁ
                Log.e("TAREA_DEBUG", "¡¡ERROR!! Ocurrió una excepción en crearTarea", e)
            }
        }
    }
        // Actualiza una tarea.
    fun actualizarTarea(tarea: Tarea) {
        viewModelScope.launch {
            repository.updateTarea(tarea)
            sincronizar()
        }
    }

    // Elimina una tarea usando soft delete.
    fun eliminarTarea(idLocal: Long) {
        viewModelScope.launch {
            repository.markAsDeleted(idLocal)
            sincronizar()
        }
    }
    // Expone la función de sincronización a la UI (ej. para un botón de "refrescar").
    fun sincronizar() {
        viewModelScope.launch {
            repository.syncWithServer()
        }
    }
    val tareasEliminadas: StateFlow<List<Tarea>> = repository.getDeletedTareas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun restaurarTarea(tarea: Tarea) {
        viewModelScope.launch {
            repository.restoreTarea(tarea)
            sincronizar() // Sincronizamos el cambio
        }
    }

}
