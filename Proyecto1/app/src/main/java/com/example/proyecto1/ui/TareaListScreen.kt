@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyecto1.viewModel.TareaViewModel

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
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.limpiarCampos()
                            navController.navigate("agregar")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar Tarea",
                            tint = Color.Magenta
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {

            items(tareas) { tarea ->
                var showDialog by remember { mutableStateOf(false) }

                Column(modifier = Modifier.padding(8.dp)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = tarea.titulo,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Checkbox(
                                    checked = tarea.completado,
                                    onCheckedChange = { isChecked ->
                                        viewModel.cambiarCompletada(tarea, isChecked)
                                    }
                                )
                            }

                            Text("Descripción: ${tarea.descripcion}", style = MaterialTheme.typography.bodyMedium)
                            Text("Fecha de asignación: ${tarea.fechaAsignada}", style = MaterialTheme.typography.bodyMedium)
                            Text("Fecha límite de entrega: ${tarea.fechaLimite}", style = MaterialTheme.typography.bodyMedium)
                            //Text("Completada: ${tarea.completado}", style = MaterialTheme.typography.bodyMedium)
                            Text("Nivel de prioridad: ${tarea.prioridad}", style = MaterialTheme.typography.bodyMedium)

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = { showDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                                ) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Borrar", tint = Color.White)
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                                Button(
                                    onClick = {
                                        viewModel.limpiarCampos()
                                        viewModel.seleccionarTareaParaEditar(tarea)
                                        navController.navigate("editar")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Editar", tint = Color.White)
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                            }
                        }
                    }
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Confirmar eliminación") },
                            text = { Text("¿Seguro que quieres borrar esta tarea?") },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDialog = false
                                        viewModel.borrarTarea(tarea)
                                        navController.navigate("listar") {
                                            popUpTo("listar") { inclusive = true }
                                        }
                                    }
                                ) {
                                    Text("Sí", color = Color.Red)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("No")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
