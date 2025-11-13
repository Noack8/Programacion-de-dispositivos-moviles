package com.example.proyecto1.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.viewModel.TaskViewModel

@Composable
fun Navegador(viewModel: TaskViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "listar") {
        composable("listar") { TareaListScreen(navController, viewModel) }
        composable("agregar") { RegTareaScreen(navController, viewModel) }
        composable("editar") { EditTareaScreen(navController, viewModel) }
    }
}