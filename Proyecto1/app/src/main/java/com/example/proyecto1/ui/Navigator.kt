package com.example.proyecto1.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto1.viewModel.UserViewModel

@Composable
fun Navigator(viewModel: UserViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { RegistroScreen(navController, viewModel) }
        composable("listar") { UserListScreen(navController, viewModel) }
        //composable("registro") { FormRegScreen(navController) }
    }
}