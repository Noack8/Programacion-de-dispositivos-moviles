package com.example.ejemplocoroutine.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    data class UiState(
        val isLoading: Boolean = false,
        val message: String = "Presiona el botón"
    )

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun simulateApiCall() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = "Cargando...") }

            val result = withContext(Dispatchers.IO) {
                //Lectura de Json o de Bases de Datos
                delay(3000) // Simula una llamada a red de 3 segundos
                "¡Respuesta de la API!"
            }

            _uiState.update { it.copy(isLoading = false, message = result) }
        }
    }
}
