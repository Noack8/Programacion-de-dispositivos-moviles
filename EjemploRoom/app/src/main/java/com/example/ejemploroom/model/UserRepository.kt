package com.example.ejemploroom.model

class UserRepository(private val dao: UsuarioDao) {
    suspend fun insert(user: User) = dao.insertar(user)
    suspend fun getAll() = dao.getAllUsers()
    suspend fun findByEmail(email: String) = dao.getUserByEmail(email)
    suspend fun findByName(name: String) = dao.getUserByName(name)
    suspend fun findByStudentId(studentId: String) = dao.getUserByStudentId(studentId)
}