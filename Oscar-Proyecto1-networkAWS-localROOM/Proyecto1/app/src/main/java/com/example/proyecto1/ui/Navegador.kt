//Ubicación: com/example/proyecto1/ui/Navegador.kt
package com.example.proyecto1.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.Proyecto1App
import com.example.proyecto1.screens.LoginScreen
import com.example.proyecto1.screens.PapeleraScreen
import com.example.proyecto1.screens.TareaListScreen
import com.example.proyecto1.viewModel.AppViewModelFactory
import com.example.proyecto1.viewModel.AuthViewModel
import com.example.proyecto1.viewModel.TareaViewModel

@Composable fun Navegador() { val navController = rememberNavController()
    // Obtenemos la instancia de nuestra clase Application
    val application = LocalContext.current.applicationContext as Proyecto1App
// Creamos la fábrica de ViewModels usando los repositorios de la Application
    val viewModelFactory = AppViewModelFactory(
        authRepository = application.authRepository,
        tareaRepository = application.tareaRepository
    )

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val authViewModel: AuthViewModel = viewModel(factory = viewModelFactory)
            LoginScreen(
                navController = navController,
                viewModel = authViewModel
            )
        }
        composable("taskList") {
            val tareaViewModel: TareaViewModel = viewModel(
                factory = viewModelFactory)
            TareaListScreen(
                navController = navController,
                viewModel = tareaViewModel
            )
        }
        composable("papelera") {
            // Es MUY IMPORTANTE que usemos el mismo factory para obtener
            // la MISMA instancia del ViewModel y compartir el estado.
            val tareaViewModel: TareaViewModel = viewModel(factory = viewModelFactory)
            PapeleraScreen(
                navController = navController,
                viewModel = tareaViewModel
            )
        }
        // ... otras rutas
    }
}