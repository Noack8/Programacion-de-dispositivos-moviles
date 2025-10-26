package com.example.proyecto1.model

class UserRepository(private val dao: UserDao) {
    suspend fun insert(user: User): Boolean {
        return try {
            dao.insertUser(user)
            true
        } catch (e: Exception) {
            android.util.Log.e("UserRepository", "Error insertando usuario", e)
            false
        }
    }
    suspend fun getAll() = dao.getAllUsers()
    suspend fun findByEmail(correo: String) = dao.getUserByEmail(correo)
    suspend fun findByName(nombre: String) = dao.getUserByName(nombre)
    suspend fun findByStudentId(boleta: String) = dao.getUserByStudentId(boleta)
}