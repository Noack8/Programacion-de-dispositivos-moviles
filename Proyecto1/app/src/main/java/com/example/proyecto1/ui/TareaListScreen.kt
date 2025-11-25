package com.example.proyecto1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.viewModel.TareaViewModel
import com.example.proyecto1.viewModel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaListScreen(navController: NavHostController, tareaViewModel: TareaViewModel, usuarioViewModel: UsuarioViewModel) {
    val userData by usuarioViewModel.userData.collectAsState()
    val tareas by tareaViewModel.tasks.collectAsState()

    // Estado para el diálogo de confirmación de eliminación
    var showDeleteDialog by remember { mutableStateOf(false) }
    var tareaToDelete by remember { mutableStateOf<Tarea?>(null) }

    // Cargar tareas cuando se inicia la pantalla
    LaunchedEffect(Unit) {
        userData?.id?.let { userId ->
            tareaViewModel.getTasks(userId)
        }
    }

    // Diálogo de confirmación para eliminar
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
                tareaToDelete = null
            },
            title = { Text("¿Estás seguro?") },
            text = { Text("¿Quieres eliminar la tarea \"${tareaToDelete?.name}\"? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        tareaToDelete?.let { tareaViewModel.deleteTask(it,userData?.id!!) }
                        showDeleteDialog = false
                        tareaToDelete = null
                    }
                ) {
                    Text("Eliminar")
                }            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        tareaToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    //Text("Tareas de ${userData?.nombre ?: "Usuario"}")
                    Text("\uD83E\uDDE0✨Lista de tareas\uD83D\uDCCB\uD83D\uDD16")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    // Botón de logout si hay usuario logueado
                    if (userData != null) {
                        IconButton(
                            onClick = {
                                usuarioViewModel.logout()
                                navController.navigate("login") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Logout, contentDescription = "Cerrar sesión")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    tareaViewModel.limpiarCampos()
                    navController.navigate("agregar")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar tarea")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Información del usuario
            if (userData != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Bienvenido, ${userData!!.nombre}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "ID: ${userData!!.id}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Lista de tareas
            if (tareas.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No hay tareas",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = "Presiona el botón + para agregar una nueva tarea",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(tareas) { tarea ->
                        TareaItemCompleta(
                            tarea = tarea,
                            onEdit = {
                                tareaViewModel.seleccionarTareaParaEditar(tarea)
                                navController.navigate("editar")
                            },
                            onDelete = {
                                tareaToDelete = tarea
                                showDeleteDialog = true
                            },
                            onToggleComplete = { completada ->
                                tareaViewModel.cambiarCompletada(tarea, completada)
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TareaItemCompleta(
    tarea: Tarea,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleComplete: (Boolean) -> Unit
) {
    // Función para formatear fechas (quitar la 'T' y lo que sigue)
    fun formatearFecha(fecha: String?): String {
        if (fecha.isNullOrBlank()) return ""
        return fecha.substringBefore("T")
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (tarea.completed) {
                MaterialTheme.colorScheme.surfaceVariant
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Título (como en la primera imagen)
            Text(
                text = "📝 ${tarea.name} 🌟",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Descripción
            if (!tarea.description.isNullOrBlank()) {
                Text(
                    text = "Descripción: 💬 ${tarea.description}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // Fecha de asignación
            if (!tarea.dateAssigned.isNullOrBlank()) {
                Text(
                    text = "Fecha de asignación: 📅 ${formatearFecha(tarea.dateAssigned)} 🕒 ${tarea.timeAssigned ?: ""}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            // Fecha límite de entrega
            if (!tarea.deadlineDate.isNullOrBlank()) {
                Text(
                    text = "Fecha de entrega: ⏰ ${formatearFecha(tarea.deadlineDate)} 🕓 ${tarea.deadlineTime ?: ""}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            // Prioridad
            Text(
                text = "Nivel de prioridad: 🔥 ${tarea.priority}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Línea divisoria
            Divider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fila de controles (Checkbox y botones)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Checkbox para marcar como completada
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = tarea.completed,
                        onCheckedChange = onToggleComplete
                    )
                    Text(
                        text = if (tarea.completed) "Completada" else "Pendiente",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Botones de editar y eliminar
                Row {
                    // Botón de eliminar
                    IconButton(
                        onClick = onDelete
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Eliminar tarea",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }

                    // Botón de editar
                    IconButton(
                        onClick = onEdit
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Editar tarea"
                        )
                    }
                }
            }
        }
    }
}