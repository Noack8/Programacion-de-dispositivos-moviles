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
    suspend fun eliminarTarea(tarea: Tarea) {
        dao.deleteTarea(tarea)
    }
    suspend fun modificarCompletada(tareaId: Int, estado: Boolean) {
        dao.actualizarCompletada(tareaId, estado)
    }
    suspend fun actualizarTarea(tarea: Tarea) {
        dao.update(tarea)
    }
}