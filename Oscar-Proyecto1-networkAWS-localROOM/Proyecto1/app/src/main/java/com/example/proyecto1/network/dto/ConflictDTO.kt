package com.example.proyecto1.network.dto

import com.example.proyecto1.model.Tarea

enum class ConflictType {
    UPDATE_CONFLICT,
    DELETE_CONFLICT
}

data class ConflictDTO(
    val taskId: Int, // Puede ser idApi o idLocal dependiendo del contexto
    val clientVersion: Tarea,
    val serverVersion: Tarea,
    val conflictType: ConflictType
)