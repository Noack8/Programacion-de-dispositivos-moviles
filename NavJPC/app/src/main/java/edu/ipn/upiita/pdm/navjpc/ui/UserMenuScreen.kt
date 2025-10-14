
package edu.ipn.upiita.pdm.navjpc.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMenuScreen(navController: NavHostController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Menú de Usuario",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("inicio")
                /*
                  Invocar una nueva pantalla UI
                    Mostrar un formulario de registro de datos
                    de usuario
            */ }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate("registro")/* acción de ajustes */ }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Ajustes"
                )
            }
        }
    )
}
