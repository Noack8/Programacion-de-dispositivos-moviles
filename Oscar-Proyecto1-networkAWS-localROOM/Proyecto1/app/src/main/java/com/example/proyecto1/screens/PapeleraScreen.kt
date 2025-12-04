//Ubicación: com/example/proyecto1/screens/PapeleraScreen.kt
package com.example.proyecto1.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.viewModel.TareaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PapeleraScreen(navController: NavController, viewModel: TareaViewModel) {
    // Observamos la nueva lista de tareas eliminadas desde el ViewModel
    val tareasEliminadas by viewModel.tareasEliminadas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Papelera") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (tareasEliminadas.isEmpty()) {
            // Mensaje para cuando no hay nada en la papelera
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "La papelera está vacía",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Lista de tareas eliminadas
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tareasEliminadas) { tarea ->
                    TareaEliminadaItem(
                        tarea = tarea,
                        onRestaurarClick = {
                            viewModel.restaurarTarea(tarea)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TareaEliminadaItem(tarea: Tarea, onRestaurarClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = tarea.nombre,
                modifier = Modifier.weight(1f) // Ocupa el espacio disponible
            )
            IconButton(onClick = onRestaurarClick) {
                Icon(
                    Icons.Default.RestoreFromTrash,
                    contentDescription = "Restaurar Tarea",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}