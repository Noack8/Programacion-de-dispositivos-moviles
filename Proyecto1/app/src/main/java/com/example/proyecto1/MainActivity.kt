package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.proyecto1.model.AppDatabase
import com.example.proyecto1.model.TareaRepository
import com.example.proyecto1.ui.Navegador
import com.example.proyecto1.viewModel.TareaViewModel
import com.example.proyecto1.ui.Navigator
import com.example.proyecto1.viewModel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "tareas.db").build()
        //val repo = TareaRepository(db.tareaDao())
        //val viewModel = TareaViewModel(repo)

        setContent {
            //Navigator(viewModel)
            val taskViewModel: TaskViewModel = viewModel()
            Navegador(viewModel = taskViewModel)
        }
    }
}
