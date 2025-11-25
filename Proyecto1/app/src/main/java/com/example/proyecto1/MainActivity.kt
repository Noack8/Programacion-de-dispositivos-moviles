package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto1.ui.Navegador
import com.example.proyecto1.viewModel.TareaViewModel
import com.example.proyecto1.viewModel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tareaViewModel: TareaViewModel = viewModel()
            val usuarioViewModel: UsuarioViewModel = viewModel()
            Navegador(usuarioViewModel,tareaViewModel)
        }
    }
}
