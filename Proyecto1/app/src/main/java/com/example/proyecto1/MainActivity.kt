package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.proyecto1.model.AppDatabase
import com.example.proyecto1.model.UserRepository
import com.example.proyecto1.viewModel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "users.db").build()
        val repo = UserRepository(db.userDao())
        val viewModel = UserViewModel(repo)

        setContent {
            MaterialTheme {
                UserListScreen(viewModel,)
            }
        }
    }
}

@Composable
fun UserListScreen(viewModel: UserViewModel, onSearch: () -> Unit) {
    val users by viewModel.users.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadUsers() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User List") },
                actions = { IconButton(onClick = onSearch) { Icon(Icons.Default.Search, null) } }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding)) {
            items(users) { user ->
                ListItem(
                    headlineText = { Text(user.nombre) },
                    supportingText = { Text(user.email) }
                )
            }
        }
    }
}