package com.example.proyecto1.model

import com.google.gson.annotations.SerializedName

data class Tarea (
    val id: Int? = null,
    @SerializedName("nombre") val name: String,
    @SerializedName("descripcion") val description: String?,
    @SerializedName("fecha_asignacion") val dateAssigned: String?,
    @SerializedName("hora_asignacion") val timeAssigned: String?,
    @SerializedName("fecha_entrega") val deadlineDate: String?,
    @SerializedName("hora_entrega") val deadlineTime: String?,
    @SerializedName("finalizada") val completed: Boolean,
    @SerializedName("prioridad") val priority: Int,
)