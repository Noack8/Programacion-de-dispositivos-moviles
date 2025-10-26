package com.example.proyecto1.model

class TareaRepository(private val dao: TareaDao) {
    suspend fun insert(tarea: Tarea): Boolean {
        return try {
            dao.insertTarea(tarea)
            true
        } catch (e: Exception) {
            android.util.Log.e("UserRepository", "Error insertando usuario", e)
            false
        }
    }
    suspend fun getAll() = dao.getAllTareas()
    /*
    suspend fun findByEmail(correo: String) = dao.getUserByEmail(correo)
    suspend fun findByName(nombre: String) = dao.getUserByName(nombre)
    suspend fun findByStudentId(boleta: String) = dao.getUserByStudentId(boleta)
    */
}