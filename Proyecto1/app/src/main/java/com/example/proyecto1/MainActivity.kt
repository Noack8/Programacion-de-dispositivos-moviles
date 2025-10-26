package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.proyecto1.model.AppDatabase
import com.example.proyecto1.model.UserRepository
import com.example.proyecto1.viewModel.UserViewModel
import com.example.proyecto1.ui.Navigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "users.db").build()
        val repo = UserRepository(db.userDao())
        val viewModel = UserViewModel(repo)

        setContent {
            Navigator(viewModel)
        }
    }
}
