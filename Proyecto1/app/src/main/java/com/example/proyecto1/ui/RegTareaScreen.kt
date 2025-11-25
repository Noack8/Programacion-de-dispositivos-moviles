@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto1.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.viewModel.TareaViewModel
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegTareaScreen(navController: NavHostController, viewModel: TareaViewModel) {
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registrar tarea") }) }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campos normales
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

            // ---- 📅 Fecha y hora asignada en la misma fila ----
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatePickerField(
                    label = "Fecha\nasignada",
                    date = viewModel.dateAssigned,
                    onDateSelected = { viewModel.dateAssigned = it },
                    error = viewModel.dateAssignedError,
                    modifier = Modifier.weight(1f)
                )
                TimePickerField(
                    label = "Hora\nasignada",
                    time = viewModel.timeAssigned,
                    onTimeSelected = { viewModel.timeAssigned = it },
                    error = viewModel.timeAssignedError,
                    modifier = Modifier.weight(1f)
                )
            }

            // ---- 📅 Fecha y hora límite en otra fila ----
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DatePickerField(
                    label = "Fecha\nlímite",
                    date = viewModel.deadlineDate,
                    onDateSelected = { viewModel.deadlineDate = it },
                    error = viewModel.deadlineDateError,
                    modifier = Modifier.weight(1f)
                )
                TimePickerField(
                    label = "Hora\nlímite",
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
                        val nuevaTarea = Tarea(
                            name = viewModel.name,
                            description = viewModel.description,
                            dateAssigned = viewModel.dateAssigned,
                            timeAssigned = viewModel.timeAssigned,
                            deadlineDate = viewModel.deadlineDate,
                            deadlineTime = viewModel.deadlineTime,
                            completed = viewModel.completed,
                            priority = viewModel.priority
                        )
                        viewModel.addTask(nuevaTarea)
                        navController.navigate("listar")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar")
            }

            Button(
                onClick = { navController.navigate("listar") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}
@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            isError = error != null,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
@Composable
fun DatePickerField(
    label: String,
    date: String,
    onDateSelected: (String) -> Unit,
    error: String? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePicker = DatePickerDialog(
        context,
        { _, y, m, d -> onDateSelected(String.format("%04d/%02d/%02d", y, m + 1, d)) },
        year, month, day
    )

    Column(modifier = modifier) {
        OutlinedTextField(
            value = date,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { datePicker.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                }
            }
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun TimePickerField(
    label: String,
    time: String,
    onTimeSelected: (String) -> Unit,
    error: String? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val timePicker = TimePickerDialog(
        context,
        { _, h, m -> onTimeSelected(String.format("%02d:%02d", h, m)) },
        hour, minute, true
    )

    Column(modifier = modifier) {
        OutlinedTextField(
            value = time,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { timePicker.show() }) {
                    Icon(Icons.Default.AccessTime, contentDescription = "Seleccionar hora")
                }
            }
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
