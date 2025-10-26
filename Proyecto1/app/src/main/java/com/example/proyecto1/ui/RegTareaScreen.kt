@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto1.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun RegTareaScreen (navController: NavHostController, viewModel: TareaViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de tarea") })
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
                label = "Nombre",
                value = viewModel.nombre,
                onValueChange = { viewModel.nombre = it },
                error = viewModel.nombreError,
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
            InputField(
                label = "Nivel de prioridad",
                value = viewModel.prioridad,
                onValueChange = { viewModel.prioridad = it },
                error = viewModel.prioridadError,
                keyboardType = KeyboardType.Number
            )
            Button(
                onClick = {
                    if (viewModel.validarCampos()){
                        val nuevaTarea = Tarea(
                            nombre = viewModel.nombre,
                            descripcion = viewModel.descripcion,
                            fechaAsignada = viewModel.fechaAsignada,
                            fechaLimite = viewModel.fechaLimite,
                            completado = false,
                            prioridad = viewModel.prioridad.toInt()
                        )
                        Log.d("PruebasJuan", "Nueva tarea: $nuevaTarea")
                        viewModel.addTarea(nuevaTarea)
                        Log.d("PruebasJuan", "Paso la prueba")
                        navController.navigate("listar")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Comprobar")
            }
            Button(
                onClick = { navController.navigate("listar") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver lista")
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