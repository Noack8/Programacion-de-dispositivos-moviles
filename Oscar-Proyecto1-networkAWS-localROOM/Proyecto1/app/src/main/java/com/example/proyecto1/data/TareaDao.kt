//Ubicación: com/example/proyecto1/data/TareaDao.kt
package com.example.proyecto1.data

import androidx.room.*
import androidx.room.Upsert
import com.example.proyecto1.model.Tarea
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {
    // MODIFICADO: Ahora solo obtiene las tareas que NO están marcadas como eliminadas.
    @Query("SELECT * FROM tareas WHERE deleted = 0 ORDER BY updated_at DESC")
    fun getAllTareas(): Flow<List<Tarea>>
    // NUEVO: Obtiene las tareas con cambios pendientes de sincronizar.
    @Query("SELECT * FROM tareas WHERE pending_sync = 1")
    suspend fun getPendingSyncTasks(): List<Tarea>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tarea: Tarea): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tareas: List<Tarea>)
    @Update
    suspend fun update(tarea: Tarea)
    // NUEVO: Marca una tarea como eliminada (soft delete).
    @Query("UPDATE tareas SET deleted = 1, deleted_at = :deletedTimestamp, pending_sync = 1, updated_at = :deletedTimestamp WHERE idLocal = :id")
    suspend fun markAsDeleted(id: Long, deletedTimestamp: Long)
    @Query("DELETE FROM tareas")
    suspend fun deleteAll()
    @Upsert
    suspend fun upsertAll(tareas: List<Tarea>)
    @Query("DELETE FROM tareas WHERE idLocal IN (:ids)")
    suspend fun deleteByLocalIds(ids: List<Long>)
    @Update
    suspend fun updateAll(tareas: List<Tarea>)
    @Query("SELECT * FROM tareas WHERE deleted = 1 ORDER BY deleted_at DESC")
    fun getDeletedTareas(): Flow<List<Tarea>>

}