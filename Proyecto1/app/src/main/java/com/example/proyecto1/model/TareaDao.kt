package com.example.proyecto1.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TareaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTarea(tarea: Tarea)
    @Query("SELECT * FROM tareas")
    suspend fun getAllTareas(): List<Tarea>
    @Delete
    suspend fun deleteTarea(tarea: Tarea)
    @Query("UPDATE tareas SET completado = :estado WHERE id = :tareaId")
    suspend fun actualizarCompletada(tareaId: Int, estado: Boolean)
    @Update
    suspend fun update(tarea: Tarea)
}