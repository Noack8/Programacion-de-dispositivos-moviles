@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto1.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyecto1.viewModel.UserViewModel

@Composable
fun UserListScreen(navController: NavHostController, viewModel: UserViewModel) {
    val users by viewModel.users.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadUsers()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("User List") },
                /*actions = {
                    IconButton(onClick = { /* TODO: acción de búsqueda */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                }*/
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Nombre: ${user.nombre}", style = MaterialTheme.typography.titleMedium)
                        Text("Correo: ${user.correo}", style = MaterialTheme.typography.bodyMedium)
                        Text("Boleta: ${user.boleta}", style = MaterialTheme.typography.bodyMedium)
                        Text("Carrera: ${user.carrera}", style = MaterialTheme.typography.bodyMedium)
                        Text("Password: ${"*".repeat(user.password.length)}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}
