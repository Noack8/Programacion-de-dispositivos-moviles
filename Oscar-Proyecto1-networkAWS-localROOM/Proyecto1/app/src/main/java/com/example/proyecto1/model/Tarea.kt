// En: com/example/proyecto1/model/Tarea.kt

package com.example.proyecto1.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**•Modelo de datos HÍBRIDO y COMPLETO para una Tarea.
 * •Contiene anotaciones para AMBAS librerías (Room y Gson) y campos de gestión local.
 * */
@Entity(tableName = "tareas")
data class Tarea(

    @PrimaryKey(autoGenerate = true)            val idLocal: Long = 0,
    @SerializedName("id")                val idApi: Int?, // El ID de la API. Nullable para tareas locales nuevas.
    @SerializedName("nombre")            val nombre: String,

                                        // --- Propiedades que coinciden con el JSON (camelCase) de la API ---
    @SerializedName("descripcion")       val descripcion: String?, // El schema dice que puede ser nulo (YES)
    @SerializedName("fecha_asignacion")  val fechaAsignacion: String, // Room guardará la fecha como texto  // El traductor perfecto: JSON "fecha_asignacion" -> Kotlin "fechaAsignacion"
    @SerializedName("hora_asignacion")   val horaAsignacion: String, // Room guardará la hora como texto
    @SerializedName("fecha_entrega")     val fechaEntrega: String?,
    @SerializedName("hora_entrega")      val horaEntrega: String?,
    @SerializedName("finalizada")        val finalizada: Boolean = false,   // El schema dice que el default es 'false', así que lo ponemos aquí también
    @SerializedName("prioridad")         val prioridad: Int?,

    @ColumnInfo(name = "usuario_id")            val usuarioId: Int?,    // Este campo lo necesitamos en Room, pero la API lo deduce del token,// así que no necesita @SerializedName.

                                        // --- NUEVOS CAMPOS PARA SOFT DELETE Y SYNC ---
    @ColumnInfo(name = "pending_sync", defaultValue = "0")  val pendingSync: Boolean = false,     // 1. Reemplaza a 'sincronizada'. Indica si hay cambios locales pendientes de enviar.
    @ColumnInfo(name = "updated_at")                        val updatedAt: Long = System.currentTimeMillis(),       // 2. Timestamp de la última modificación. Clave para resolver conflictos.
    @ColumnInfo(name = "deleted", defaultValue = "0")       val deleted: Boolean = false,       // 3. Flag para borrado lógico (soft delete).
    @ColumnInfo(name = "deleted_at")                        val deletedAt: Long? = null     // 4. Timestamp del borrado lógico.
)