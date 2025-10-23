package com.example.proyecto1.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface userDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(usuario: User)

    @Query("SELECT * FROM usuarios WHERE correo = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM usuarios WHERE nombre = :nombre")
    suspend fun getUserByName(nombre: String): User

    @Query("SELECT * FROM usuarios")
    suspend fun getAllUsers(): List<User>

    @Query("SELECT * FROM usuarios WHERE boleta = :boleta")
    suspend fun getUserByStudentId(boleta: String): User?
}