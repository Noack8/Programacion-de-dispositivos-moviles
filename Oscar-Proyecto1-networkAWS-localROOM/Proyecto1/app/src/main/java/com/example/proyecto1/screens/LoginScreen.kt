//Ubicación: com/example/proyecto1/screens/LoginScreen.kt
package com.example.proyecto1.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.proyecto1.viewModel.AuthViewModel

@Composable fun LoginScreen( navController: NavController, viewModel: AuthViewModel ) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

// Efecto para navegar cuando el login es exitoso
    LaunchedEffect(loginState) {
        if (loginState is AuthViewModel.LoginState.Success) {
            Toast.makeText(context, "Login Exitoso", Toast.LENGTH_SHORT).show()
            navController.navigate("taskList") {
                // Borra la pantalla de login del stack para no volver a ella con el botón de atrás
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        // Mostrar un indicador de carga si se está procesando el login
        if (loginState is AuthViewModel.LoginState.Loading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }
        }

        // Mostrar mensaje de error si el login falla
        if (loginState is AuthViewModel.LoginState.Error) {
            val errorState = loginState as AuthViewModel.LoginState.Error
            Text(
                text = errorState.message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}