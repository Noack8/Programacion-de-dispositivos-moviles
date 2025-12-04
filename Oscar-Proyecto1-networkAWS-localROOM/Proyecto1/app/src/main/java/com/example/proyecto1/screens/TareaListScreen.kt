//Ubicación: com/example/proyecto1/screens/TareaListScreen.kt
package com.example.proyecto1.screens

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material3.DismissValue
//import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.DismissDirection

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.viewModel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun TareaListScreen(
    navController: NavController, viewModel: TareaViewModel ) {         // Observamos el StateFlow del ViewModel.
    val tareas by viewModel.tareas.collectAsStateWithLifecycle()   // collectAsStateWithLifecycle es la forma segura y optimizada de hacerlo.
    var showDialog by remember { mutableStateOf(false) }
// Al iniciar la pantalla, llamamos a sincronizar por si hay cambios pendientes.
    LaunchedEffect(Unit) {
        viewModel.sincronizar()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Tareas") },
                actions = {
                    IconButton(onClick = { navController.navigate("papelera") }) {
                        Icon(Icons.Filled.RestoreFromTrash, contentDescription = "Ver Papelera")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Tarea")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                if (tareas.isEmpty()) {
                    Text(
                        text = "¡No tienes ninguna tarea!\nAgrega una con el botón +",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }else {                        // Si hay tareas, las mostramos en una lista perezosa (eficiente).
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp) // Añade espacio entre items
                    ) {
                        items(tareas,
                            key = { tarea -> tarea.idLocal } // Key para mejorar rendimiento
                        ) { tarea ->
                            TareaItem(
                                tarea = tarea,
                                viewModel = viewModel
                            )
                         }
                     }
                    }
                }
    }


// Si `showDialog` es true, mostramos nuestro diálogo.
    if (showDialog) {
        AddTaskDialog(
            onDismiss = { showDialog = false },
            onConfirm = { textoDeLaTarea ->
                Log.d("TAREA_DEBUG","AddTaskDialog onConfirm: Se va a llamar a viewModel.crearTarea")
                viewModel.crearTarea(textoDeLaTarea,descripcion=textoDeLaTarea)
                showDialog = false
            }
        )
    }
}
// --- TAREAITEM MODIFICADO CON SWIPE Y LÓGICA (VERSIÓN CORREGIDA Y EXPLÍCITA) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaItem(tarea: Tarea, viewModel: TareaViewModel) {

    // CAMBIO 1: Usamos la nueva función de estado `rememberSwipeToDismissBoxState`.
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { targetValue ->
            // CAMBIO 2: Comparamos con el nuevo enum `SwipeToDismissBoxValue`.
            if (targetValue == SwipeToDismissBoxValue.EndToStart || targetValue == SwipeToDismissBoxValue.StartToEnd) {
                viewModel.eliminarTarea(tarea.idLocal)
                true // Confirmamos que la acción de "dismiss" se completó.
            } else {
                false
            }
        }
    )


// CAMBIO 3: Usamos el nuevo Composable `SwipeToDismissBox`.
    SwipeToDismissBox(
        state = dismissState,
        // La dirección del swipe ahora se define aquí dentro, no en el estado.
        enableDismissFromStartToEnd = true, // Permite deslizar de izquierda a derecha
        enableDismissFromEndToStart = true, // Permite deslizar de derecha a izquierda

        // CAMBIO 4: El parámetro `background` ahora se llama `backgroundContent`.
        backgroundContent = {
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    // El enum sigue siendo similar aquí.
                    SwipeToDismissBoxValue.EndToStart, SwipeToDismissBoxValue.StartToEnd -> Color.Red.copy(alpha = 0.8f)
                    else -> Color.Transparent
                }
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd // Alinea el ícono a la derecha
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White)
            }
        }
    ) {
        // El contenido principal (la Card) va aquí, dentro de las llaves del SwipeToDismissBox.
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = tarea.finalizada,
                    onCheckedChange = {
                        val tareaActualizada = tarea.copy(finalizada = !tarea.finalizada)
                        viewModel.actualizarTarea(tareaActualizada)
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = tarea.nombre,
                    modifier = Modifier.weight(1f),
                    textDecoration = if (tarea.finalizada) TextDecoration.LineThrough else null,
                    color = if (tarea.finalizada) Color.Gray else LocalContentColor.current
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = if (tarea.pendingSync) Icons.Default.CloudOff else Icons.Default.CloudDone,
                    contentDescription = if (tarea.pendingSync) "Cambios pendientes" else "Sincronizado",
                    tint = if (tarea.pendingSync) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )

                }
            }
        }

}



// El diálogo para añadir una nueva tarea
@Composable fun AddTaskDialog( onDismiss: () -> Unit,
                               onConfirm: (String) -> Unit ) {
    var texto by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Tarea") },
        text = {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (texto.isNotBlank()) {
                        onConfirm(texto)
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )

}
