package com.example.ejemploroom.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejemploroom.model.User
import com.example.ejemploroom.model.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> get() = _selectedUser

    fun loadUsers() {
        viewModelScope.launch {
            _users.value = repository.getAll()
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            repository.insert(user)
            loadUsers()
        }
    }

    fun findUserByEmail(email: String) {
        viewModelScope.launch {
            _selectedUser.value = repository.findByEmail(email)
        }
    }

    fun findUserByName(name: String) {
        viewModelScope.launch {
            _selectedUser.value = repository.findByName(name)
        }
    }

    fun findUserByStudentId(studentId: String) {
        viewModelScope.launch {
            _selectedUser.value = repository.findByStudentId(studentId)
        }
    }
}