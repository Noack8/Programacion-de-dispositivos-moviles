@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.practica2.ui

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
import com.example.practica2.model.User
import com.example.practica2.viewModel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistroScreen (navController: NavHostController, viewModel: UserViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de usuario") })
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
                label = "Correo",
                value = viewModel.correo,
                onValueChange = { viewModel.correo = it },
                error = viewModel.correoError,
                keyboardType = KeyboardType.Email
            )
            InputField(
                label = "Boleta",
                value = viewModel.boleta,
                onValueChange = { viewModel.boleta = it },
                error = viewModel.boletaError,
                keyboardType = KeyboardType.Number
            )
            InputField(
                label = "Carrera",
                value = viewModel.carrera,
                onValueChange = { viewModel.carrera = it },
                error = viewModel.carreraError,
                keyboardType = KeyboardType.Text
            )
            InputField(
                label = "Contraseña",
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                error = viewModel.passwordError,
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            InputField(
                label = "Confirmar contraseña",
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.confirmPassword = it },
                error = viewModel.confirmPasswordError,
                keyboardType = KeyboardType.Password,
                isPassword = true
            )
            Button(
                onClick = {
                    if (viewModel.validarCampos()){
                        val nuevoUsuario = User(
                            boleta = viewModel.boleta,
                            nombre = viewModel.nombre,
                            correo = viewModel.correo,
                            carrera = viewModel.carrera,
                            password = viewModel.password
                        )
                        Log.d("PruebasJuan", "Nuevo usuario: $nuevoUsuario")
                        viewModel.addUser(nuevoUsuario)
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