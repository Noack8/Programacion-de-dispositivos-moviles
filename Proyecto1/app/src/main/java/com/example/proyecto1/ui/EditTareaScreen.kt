@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto1.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyecto1.model.Task
import com.example.proyecto1.viewModel.TaskViewModel
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditTareaScreen(navController: NavHostController, viewModel: TaskViewModel) {
    val tareaEdit = viewModel.selectedTarea.collectAsState().value
    if (tareaEdit == null) {
        LaunchedEffect(Unit) { navController.navigate("listar") }
        return
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Editar tarea") }) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campos básicos
            InputField(
                label = "Título",
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                error = viewModel.nameError,
                keyboardType = KeyboardType.Text
            )

            InputField(
                label = "Descripción",
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                error = viewModel.descriptionError,
                keyboardType = KeyboardType.Text
            )

            // ---- 📅 Fecha y hora asignada (misma fila)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatePickerField(
                    label = "Fecha asignada",
                    date = viewModel.dateAssigned,
                    onDateSelected = { viewModel.dateAssigned = it },
                    error = viewModel.dateAssignedError,
                    modifier = Modifier.weight(1f)
                )
                TimePickerField(
                    label = "Hora asignada",
                    time = viewModel.timeAssigned,
                    onTimeSelected = { viewModel.timeAssigned = it },
                    error = viewModel.timeAssignedError,
                    modifier = Modifier.weight(1f)
                )
            }

            // ---- 📅 Fecha y hora límite (otra fila)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatePickerField(
                    label = "Fecha límite",
                    date = viewModel.deadlineDate,
                    onDateSelected = { viewModel.deadlineDate = it },
                    error = viewModel.deadlineDateError,
                    modifier = Modifier.weight(1f)
                )
                TimePickerField(
                    label = "Hora límite",
                    time = viewModel.deadlineTime,
                    onTimeSelected = { viewModel.deadlineTime = it },
                    error = viewModel.deadlineTimeError,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---- Nivel de prioridad ----
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Nivel de prioridad: ${viewModel.priority}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = viewModel.priority.toFloat(),
                    onValueChange = { viewModel.priority = it.toInt().coerceIn(1, 10) },
                    valueRange = 1f..10f,
                    steps = 8,
                    modifier = Modifier.fillMaxWidth()
                )
                if (viewModel.priorityError != null) {
                    Text(
                        text = viewModel.priorityError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ---- Botones ----
            Button(
                onClick = {
                    if (viewModel.validarCampos()) {
                        val tareaActualizada = Task(
                            id = tareaEdit.id,
                            name = viewModel.name,
                            description = viewModel.description,
                            dateAssigned = viewModel.dateAssigned,
                            timeAssigned = viewModel.timeAssigned,
                            deadlineDate = viewModel.deadlineDate,
                            deadlineTime = viewModel.deadlineTime,
                            completed = viewModel.completed,
                            priority = viewModel.priority
                        )
                        viewModel.actualizarTask(tareaActualizada)
                        navController.navigate("listar")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { navController.navigate("listar") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}