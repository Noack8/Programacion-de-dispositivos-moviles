@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto1.ui


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.viewModel.TareaViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegTareaScreen(navController: NavHostController, viewModel: TareaViewModel) {

    // Estados para controlar la visibilidad de los diálogos
    var showDatePickerAsignada by remember { mutableStateOf(false) }
    var showDatePickerLimite by remember { mutableStateOf(false) }
    var showTimePickerAsignada by remember { mutableStateOf(false) }
    var showTimePickerLimite by remember { mutableStateOf(false) }

    // Limpiar campos al entrar en la pantalla por primera vez
    LaunchedEffect(Unit) {
        viewModel.limpiarCampos()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registrar de tarea") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues) // Usar el padding del Scaffold
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), // Permite hacer scroll si el contenido no cabe
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

            // --- Campo de Fecha Asignada (Corregido) ---
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
                )
            }
            if (showTimePickerLimite) {
                MyTimePickerDialog(
                    onTimeSelected = { viewModel.horaLimite = it },
                    onDismiss = { showTimePickerLimite = false }
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
                        // Crear el objeto Tarea con todos los campos, incluyendo la hora
                        val nuevaTarea = Tarea(
                            titulo = viewModel.titulo,
                            descripcion = viewModel.descripcion,
                            fechaAsignada = viewModel.fechaAsignada,
                            fechaLimite = viewModel.fechaLimite,
                            horaAsignada = viewModel.horaAsignada,
                            horaLimite = viewModel.horaLimite,
                            completado = false,
                            prioridad = viewModel.prioridad.toIntOrNull() ?: 1
                        )
                        viewModel.addTarea(nuevaTarea)
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


// ===================================================================
// --- COMPONENTES REUTILIZABLES ---
// Lo ideal es mover estos componentes a un archivo separado,
// por ejemplo, "CommonUI.kt", para no repetir código.
// ===================================================================

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            isError = error != null,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = VisualTransformation.None
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
fun DateField(
    label: String,
    value: String,
    onClick: () -> Unit, // Cambiado de 'onclick' a 'onClick' por convención
    error: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Seleccionar fecha",
                    modifier = Modifier.clickable(onClick = onClick)
                )
            },
            isError = error != null,
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
fun TimeField(
    label: String,
    value: String,
    onClick: () -> Unit,
    error: String? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = "Seleccionar hora",
                    modifier = Modifier.clickable(onClick = onClick)
                )
            },
            isError = error != null,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    fechaInicioSeleccion: String? = null, // Para deshabilitar fechas ANTES de esta
    fechaLimiteParaSeleccion: String? = null  // Para deshabilitar fechas DESPUÉS de esta
) {
    // --- Lógica para convertir las fechas de String a milisegundos (Long) ---
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    formato.timeZone = TimeZone.getTimeZone("UTC")

    val millisInicio = fechaInicioSeleccion?.takeIf { it.isNotBlank() }?.let {
        try { formato.parse(it)?.time } catch (e: Exception) { null }
    }

    val millisLimite = fechaLimiteParaSeleccion?.takeIf { it.isNotBlank() }?.let {
        try { formato.parse(it)?.time } catch (e: Exception) { null }
    }
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            val hoy = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis
            val fechaInicioMillis = millisInicio ?: hoy

            // La fecha es seleccionable si:
            // 1. Es posterior o igual a la fecha de inicio (o a hoy si no hay fecha de inicio)
            val esDespuesDeInicio = utcTimeMillis >= fechaInicioMillis

            // 2. Es anterior o igual a la fecha límite (si se proporciona una)
            val esAntesDeLimite = if (millisLimite != null) {
                utcTimeMillis <= millisLimite
            } else {
                true // Si no hay límite, siempre es válido
            }

            return esDespuesDeInicio && esAntesDeLimite
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC") // Evita problemas de zona horaria
        formatter.format(Date(it))
    } ?: ""

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (selectedDate.isNotEmpty()) {
                    onDateSelected(selectedDate)
                }
                onDismiss()
            }) { Text("OK") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTimePickerDialog(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    esFechaLimiteElMismoDia: Boolean = false,
    horaInicioSeleccion: String = ""
) {
    val timePickerState = rememberTimePickerState(
        initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        initialMinute = 0,
        is24Hour = false
    )
// Estado para el mensaje de error dentro del diálogo
    var errorMensaje by remember { mutableStateOf<String?>(null) }
    // Función para convertir la hora en formato "h:mm a" a un objeto Calendar
    fun parseHora(horaStr: String): Calendar? {
        return try {
            val formato = SimpleDateFormat("h:mm a", Locale.getDefault())
            val date = formato.parse(horaStr)
            Calendar.getInstance().apply { time = date }
        } catch (e: Exception) {
            null
        }
    }

    // Comprobamos la validez de la hora cada vez que cambia
    LaunchedEffect(timePickerState.hour, timePickerState.minute) {
        errorMensaje = null // Limpiar error anterior
        if (esFechaLimiteElMismoDia && horaInicioSeleccion.isNotBlank()) {
            val calInicio = parseHora(horaInicioSeleccion)
            if (calInicio != null) {
                val calActual = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    set(Calendar.MINUTE, timePickerState.minute)
                }

                if (calActual.before(calInicio)) {
                    errorMensaje = "La hora límite no puede ser anterior a la de asignación."
                }
            }
        }
    }
    val selectedTime = String.format(
        Locale.getDefault(),
        "%d:%02d %s",
        if (timePickerState.hour == 0 || timePickerState.hour == 12) 12 else timePickerState.hour % 12,
        timePickerState.minute,
        if (timePickerState.hour < 12) "AM" else "PM"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                onTimeSelected(selectedTime)
                onDismiss()
            },// Deshabilitar el botón OK si hay un error
                enabled=errorMensaje==null
            ){  Text("OK")}
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("Cancelar") }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TimePicker(state = timePickerState)
                //mostrar el mensaje de erro si existe
                if (errorMensaje != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMensaje ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    )
}
