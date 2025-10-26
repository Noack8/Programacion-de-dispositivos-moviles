package com.example.proyecto1.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TareaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTarea(tarea: Tarea)
    @Query("SELECT * FROM tareas")
    suspend fun getAllTareas(): List<Tarea>
    /*
    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    suspend fun getUserByEmail(correo: String): User?

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre")
    suspend fun getUserByName(nombre: String): User?

    @Query("SELECT * FROM usuarios WHERE boleta = :boleta")
    suspend fun getUserByStudentId(boleta: String): User?
    */
}