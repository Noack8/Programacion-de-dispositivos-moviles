package com.example.proyecto1.model

data class TareaCreateRequest(
    val nombre: String,
    val descripcion: String?,
    val fecha_asignacion: String?,
    val hora_asignacion: String?,
    val fecha_entrega: String?,
    val hora_entrega: String?,
    val finalizada: Boolean,
    val prioridad: Int,
    val usuario_id: Int
)
