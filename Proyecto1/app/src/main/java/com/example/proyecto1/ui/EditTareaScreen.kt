@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto1.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.viewModel.TareaViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditTareaScreen(navController: NavHostController, viewModel: TareaViewModel) {

    val tareaEdit = viewModel.selectedTarea.collectAsState().value
    if (tareaEdit == null) {
        // Si no hay tarea seleccionada, vuelve a la lista para evitar un crash.
        LaunchedEffect(Unit) { navController.navigate("listar") }
        return
    }

    // Estados para controlar la visibilidad de los diálogos
    var showDatePickerAsignada by remember { mutableStateOf(false) }
    var showDatePickerLimite by remember { mutableStateOf(false) }
    var showTimePickerAsignada by remember { mutableStateOf(false) }
    var showTimePickerLimite by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Tarea") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                label = "Título",
                value = viewModel.titulo,
                onValueChange = { viewModel.titulo = it },
                error = viewModel.tituloError
            )
            InputField(
                label = "Descripción",
                value = viewModel.descripcion,
                onValueChange = { viewModel.descripcion = it },
                error = viewModel.descripcionError
            )

            // --- Campos de Fecha y Hora (igual que en RegTareaScreen) ---
            // --- Fila para los campos de Fecha ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre los elementos
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    DateField(
                        label = "Fecha asignada", // Etiqueta más corta
                        value = viewModel.fechaAsignada,
                        onClick = { showDatePickerAsignada = true },
                        error = viewModel.fechaAsignadaError
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    DateField(
                        label = "Fecha límite", // Etiqueta más corta
                        value = viewModel.fechaLimite,
                        onClick = { showDatePickerLimite = true },
                        error = viewModel.fechaLimiteError
                    )
                }
            }

// --- Fila para los campos de Hora ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre los elementos
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    TimeField(
                        label = "Hora asignada",
                        value = viewModel.horaAsignada,
                        onClick = { showTimePickerAsignada = true },
                        error = viewModel.horaAsignadaError
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    TimeField(
                        label = "Hora límite",
                        value = viewModel.horaLimite,
                        onClick = { showTimePickerLimite = true },
                        error = viewModel.horaLimiteError
                    )
                }
            }


            // --- Diálogos para Fecha ---
            if (showDatePickerAsignada) {
                MyDatePickerDialog(
                    onDateSelected = { viewModel.fechaAsignada = it },
                    onDismiss = { showDatePickerAsignada = false },
                    fechaLimiteParaSeleccion = viewModel.fechaLimite
                )
            }
            if (showDatePickerLimite) {
                MyDatePickerDialog(
                    onDateSelected = { viewModel.fechaLimite = it },
                    onDismiss = { showDatePickerLimite = false },
                    fechaInicioSeleccion = viewModel.fechaAsignada
                )
            }

            // --- Diálogos para Hora ---
            if (showTimePickerAsignada) {
                MyTimePickerDialog(
                    onTimeSelected = { viewModel.horaAsignada = it },
                    onDismiss = { showTimePickerAsignada = false }
                    // El diálogo de la hora de asignación no necesita validación extra.
                )
            }
            if (showTimePickerLimite) {
                MyTimePickerDialog(
                    onTimeSelected = { viewModel.horaLimite = it },
                    onDismiss = { showTimePickerLimite = false },
                    // --- PARÁMETROS MOVIDOS A SU LUGAR CORRECTO ---
                    esFechaLimiteElMismoDia = viewModel.fechaAsignada == viewModel.fechaLimite,
                    horaInicioSeleccion = viewModel.horaAsignada
                )
            }

            // --- Selector de Prioridad ---
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Nivel de prioridad: ${viewModel.prioridad.ifBlank { "1" }}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Slider(
                    value = (viewModel.prioridad.toFloatOrNull() ?: 1f),
                    onValueChange = { viewModel.prioridad = it.toInt().coerceIn(1, 10).toString() },
                    valueRange = 1f..10f,
                    steps = 8,
                    modifier = Modifier.fillMaxWidth()
                )
                if (viewModel.prioridadError != null) {
                    Text(
                        text = viewModel.prioridadError ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // --- Botones de Acción ---
            Button(
                onClick = {
                    if (viewModel.validarCampos()) {
                        val tareaActualizada = Tarea(
                            id = tareaEdit.id, // Usamos el id de la tarea que estamos editando
                            titulo = viewModel.titulo,
                            descripcion = viewModel.descripcion,
                            fechaAsignada = viewModel.fechaAsignada,
                            fechaLimite = viewModel.fechaLimite,
                            horaAsignada = viewModel.horaAsignada, // Pasamos la hora
                            horaLimite = viewModel.horaLimite,     // Pasamos la hora
                            completado = tareaEdit.completado, // Mantenemos el estado de 'completado' original
                            prioridad = viewModel.prioridad.toIntOrNull() ?: 1
                        )

                        viewModel.actualizarTarea(tareaActualizada)
                        // Navegamos de vuelta limpiando la pila para no poder volver a la pantalla de edición
                        navController.navigate("listar") {
                            popUpTo("listar") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    navController.navigate("listar") {
                        popUpTo("listar") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}
