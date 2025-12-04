    //upicacion: com/example/proyecto1/MainActivity.kt
package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.proyecto1.ui.Navegador // Asegúrate que el import sea correcto
import com.example.proyecto1.ui.theme.Proyecto1Theme
import com.example.proyecto1.model.AppDatabase
import com.example.proyecto1.network.RetrofitClient
import com.example.proyecto1.repository.AuthRepository
import com.example.proyecto1.repository.TareaRepository
import com.example.proyecto1.screens.LoginScreen
import com.example.proyecto1.screens.TareaListScreen
import com.example.proyecto1.viewModel.AppViewModelFactory
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Proyecto1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // La única responsabilidad de MainActivity es llamar al navegador.
                    Navegador()
                }
            }
        }
    }
}