// Ubicación: com/example/proyecto1/viewModel/AppViewModelFactory.kt

package com.example.proyecto1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto1.repository.AuthRepository
import com.example.proyecto1.repository.TareaRepository

class AppViewModelFactory(
    private val authRepository: AuthRepository,
    private val tareaRepository: TareaRepository // <-- El repositorio ya viene construido
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(TareaViewModel::class.java) -> {
                TareaViewModel(tareaRepository) as T // <-- Le pasamos el TareaRepository
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}