package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto1.ui.Navegador
import com.example.proyecto1.viewModel.TareaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val taskViewModel: TareaViewModel = viewModel()
            Navegador(viewModel = taskViewModel)
        }
    }
}
