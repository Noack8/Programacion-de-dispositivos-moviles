package com.example.proyecto1.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.viewModel.UserViewModel

@Composable
fun Navigator(viewModel: UserViewModel, onSearch: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { LoginScreen(navController) }
        composable("Registrarse") { RegistrarUsuario(navController) }
        composable("registro") { UserListScreen (navController) }
    }
}