package com.example.proyecto1.model

class UserRepository(private val dao: userDao) {
    suspend fun insert(user: User) = dao.insertar(user)
    suspend fun getAll() = dao.getAllUsers()
    suspend fun findByEmail(email: String) = dao.getUserByEmail(email)
    suspend fun findByName(name: String) = dao.getUserByName(name)
    suspend fun findByStudentId(studentId: String) = dao.getUserByStudentId(studentId)
}