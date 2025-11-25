package com.example.proyecto1.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.viewModel.TareaViewModel
import com.example.proyecto1.viewModel.UsuarioViewModel

@Composable
fun Navegador(usuarioViewModel: UsuarioViewModel, tareaViewModel: TareaViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, usuarioViewModel) }
        composable("listar") { TareaListScreen(navController, tareaViewModel, usuarioViewModel) }
        composable("agregar") { RegTareaScreen(navController, tareaViewModel, usuarioViewModel) }
        composable("editar") { EditTareaScreen(navController, tareaViewModel) }
    }
}