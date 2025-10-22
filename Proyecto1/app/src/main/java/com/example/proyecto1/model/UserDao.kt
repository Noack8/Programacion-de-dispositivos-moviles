package com.example.proyecto1.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(usuario: User)

    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    suspend fun obtenerPorCorreo(correo: String): User?
}