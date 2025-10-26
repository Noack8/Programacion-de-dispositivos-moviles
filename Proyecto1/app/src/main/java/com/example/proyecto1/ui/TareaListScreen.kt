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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.viewModel.TareaViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color

@Composable
fun TareaListScreen(navController: NavHostController, viewModel: TareaViewModel) {
    val tareas by viewModel.tareas.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadTareas()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Tareas") },
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {

            // Botón "Agregar Tarea" al inicio de la lista
            item {
                Button(
                    onClick = { navController.navigate("agregar") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Agregar Tarea")
                }
            }

            // Lista de tareas
            items(tareas) { tarea ->
                Column(modifier = Modifier.padding(8.dp)) {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Nombre: ${tarea.nombre}", style = MaterialTheme.typography.titleMedium)
                            Text("Descripcion: ${tarea.descripcion}", style = MaterialTheme.typography.bodyMedium)
                            Text("Fecha de asignacion: ${tarea.fechaAsignada}", style = MaterialTheme.typography.bodyMedium)
                            Text("Fecha limite de entrega: ${tarea.fechaLimite}", style = MaterialTheme.typography.bodyMedium)
                            Text("Completada: ${tarea.completado}", style = MaterialTheme.typography.bodyMedium)
                            Text("Nivel de prioridad: ${tarea.prioridad}", style = MaterialTheme.typography.bodyMedium)

                            // Botón de editar más pequeño
                            Button(
                                onClick = { /*navController.navigate("editar")*/ },
                                modifier = Modifier.padding(top = 8.dp) // sin fillMaxWidth()
                            ) {
                                Text("Editar")
                            }
                        }
                    }
                }
            }
        }
    }
}
