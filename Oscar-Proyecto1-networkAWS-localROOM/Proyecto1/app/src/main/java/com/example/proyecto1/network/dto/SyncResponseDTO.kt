package com.example.proyecto1.network.dto

import com.example.proyecto1.model.Tarea

data class SyncResponseDTO(
    val updatedTasks: List<Tarea>,
    val conflicts: List<ConflictDTO>
)