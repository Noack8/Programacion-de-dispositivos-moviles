@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto1.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.viewModel.TareaViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EditTareaScreen(navController: NavHostController, viewModel: TareaViewModel) {

    val tareaEdit = viewModel.selectedTarea.collectAsState().value
    if (tareaEdit == null) {
        LaunchedEffect(Unit) { navController.navigate("listar") }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Tarea") })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                label = "Titulo",
                value = viewModel.titulo,
                onValueChange = { viewModel.titulo = it },
                error = viewModel.tituloError,
                keyboardType = KeyboardType.Text
            )
            InputField(
                label = "Descripcion",
                value = viewModel.descripcion,
                onValueChange = { viewModel.descripcion = it },
                error = viewModel.descripcionError,
                keyboardType = KeyboardType.Text
            )
            InputField(
                label = "Fecha asignada de la Tarea",
                value = viewModel.fechaAsignada,
                onValueChange = { viewModel.fechaAsignada = it },
                error = viewModel.fechaAsignadaError,
                keyboardType = KeyboardType.Text
            )
            InputField(
                label = "Fecha limite de entrega",
                value = viewModel.fechaLimite,
                onValueChange = { viewModel.fechaLimite = it },
                error = viewModel.fechaLimiteError,
                keyboardType = KeyboardType.Text
            )

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

            Button(
                onClick = {
                    if (viewModel.validarCampos()) {
                        val tarea = Tarea(
                            id = tareaEdit.id,
                            titulo = viewModel.titulo,
                            descripcion = viewModel.descripcion,
                            fechaAsignada = viewModel.fechaAsignada,
                            fechaLimite = viewModel.fechaLimite,
                            completado = viewModel.completado,
                            prioridad = viewModel.prioridad.toInt()
                        )

                        viewModel.actualizarTarea(tarea)
                        navController.navigate("listar")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate("listar")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}
