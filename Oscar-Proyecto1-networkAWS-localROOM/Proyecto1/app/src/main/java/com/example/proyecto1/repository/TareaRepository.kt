//Ubicación: com/example/proyecto1/repository/TareaRepository.kt

package com.example.proyecto1.repository

import android.util.Log
import com.example.proyecto1.data.TareaDao
import com.example.proyecto1.model.Tarea
import com.example.proyecto1.network.TaskApiService
import com.example.proyecto1.network.dto.ConflictDTO
import com.example.proyecto1.network.dto.SyncResponseDTO
import kotlinx.coroutines.flow.Flow

// INICIO DE LA CLASE
class TareaRepository(
    private val tareaDao: TareaDao,
    private val apiService: TaskApiService
) {

    // --- MÉTODOS PÚBLICOS ---
    fun getTareas(): Flow<List<Tarea>> = tareaDao.getAllTareas()

    suspend fun createTarea(nombre: String, descripcion: String) {
        val now = System.currentTimeMillis()
        val nuevaTarea = Tarea(
            idApi = null,
            nombre = nombre,
            descripcion = descripcion,
            fechaAsignacion = "",
            horaAsignacion = "",
            fechaEntrega = null,
            horaEntrega = null,
            finalizada = false,
            prioridad = 1,
            usuarioId = null,
            pendingSync = true,
            updatedAt = now,
            deleted = false,
            deletedAt = null
        )
        try {
            val nuevoId = tareaDao.insert(nuevaTarea)
            Log.d("TAREA_DEBUG", "Repository.createTarea: Tarea insertada en Room con idLocal: $nuevoId")
            if (nuevoId <= 0) {
                Log.e("TAREA_DEBUG", "¡¡ERROR!! La inserción en la base de datos falló y devolvió $nuevoId.")
            }
        } catch (e: Exception) {
            Log.e("TAREA_DEBUG", "¡¡ERROR!! La inserción en la base de datos lanzó una excepción.", e)
        }
    }

    suspend fun updateTarea(tarea: Tarea) {
        val now = System.currentTimeMillis()
        val tareaActualizada = tarea.copy(updatedAt = now, pendingSync = true)
        tareaDao.update(tareaActualizada)
    }

    suspend fun markAsDeleted(idLocal: Long) {
        val now = System.currentTimeMillis()
        tareaDao.markAsDeleted(idLocal, now)
    }

    suspend fun syncWithServer() {
        Log.d("TAREA_DEBUG", "Repository.syncWithServer: La funcion ha sido llamada")
        try {
            val tareasParaSincronizar = tareaDao.getPendingSyncTasks()
            if (tareasParaSincronizar.isEmpty()) {
                Log.d("TareaRepository", "No hay cambios locales pendientes. Sincronización finalizada.")
                return
            }
            Log.d("TareaRepository", "Enviando ${tareasParaSincronizar.size} tareas al servidor...")
            val syncResponse = apiService.syncTasks(tareasParaSincronizar)
            Log.d("TareaRepository", "Respuesta del servidor recibida.")
            handleSyncResponse(syncResponse, tareasParaSincronizar)
        } catch (e: Exception) {
            Log.e("TareaRepository", "Error durante la sincronización con el servidor", e)
        }
    }


    // --- MÉTODOS PRIVADOS DE SINCRONIZACIÓN ---
    private suspend fun handleSyncResponse(response: SyncResponseDTO, tareasEnviadas: List<Tarea>) {
        if (response.conflicts.isNotEmpty()) {
            Log.w("TareaRepository", "Se encontraron ${response.conflicts.size} conflictos.")
            handleConflicts(response.conflicts)
        }

        if (response.updatedTasks.isNotEmpty()) {
            Log.d("TareaRepository", "Procesando ${response.updatedTasks.size} tareas actualizadas desde el servidor.")
            val tareasParaUpsertar = mutableListOf<Tarea>()

            response.updatedTasks.forEach { tareaServidor ->
                val tareaLocalOriginal = tareasEnviadas.find { local ->
                    (local.idApi != null && local.idApi == tareaServidor.idApi) ||
                            (local.idApi == null && local.nombre == tareaServidor.nombre && local.descripcion == tareaServidor.descripcion)
                }

                if (tareaLocalOriginal != null) {
                    val tareaActualizada = tareaLocalOriginal.copy(
                        idApi = tareaServidor.idApi,
                        pendingSync = false,
                        updatedAt = tareaServidor.updatedAt
                    )
                    tareasParaUpsertar.add(tareaActualizada)
                } else {
                    tareasParaUpsertar.add(tareaServidor.copy(pendingSync = false))
                }
            }
            if (tareasParaUpsertar.isNotEmpty()) {
                tareaDao.upsertAll(tareasParaUpsertar)
                Log.d("TareaRepository", "${tareasParaUpsertar.size} tareas actualizadas/insertadas en Room vía Upsert.")
            }
        }
        // --- ¡¡NUEVA LÓGICA PARA ARREGLAR EL PUNTO FIJO!! ---
// 3. Marcar el resto de tareas enviadas como sincronizadas.
// Esto es crucial para las eliminaciones, que no vuelven en `updatedTasks`.
        // Obtenemos los IDs de las tareas que ya manejamos (actualizadas o en conflicto).
        val idsManejados = response.updatedTasks.map { it.idApi } + response.conflicts.map { it.taskId }
// Filtramos las tareas que enviamos pero que no tuvieron ni actualización ni conflicto.
        val tareasSincronizadasSilenciosamente = tareasEnviadas.filter {
            // Nos aseguramos que la tarea tenga idApi para no afectar a las nuevas que acaban de ser actualizadas
            it.idApi != null && it.idApi !in idsManejados
        }

        if (tareasSincronizadasSilenciosamente.isNotEmpty()) {
            val tareasParaMarcarComoSincronizadas = tareasSincronizadasSilenciosamente.map {
                it.copy(pendingSync = false)
            }
            // Usamos update en lugar de upsert para asegurarnos de que solo modificamos estas.
            tareaDao.updateAll(tareasParaMarcarComoSincronizadas)
            Log.d("TareaRepository", "${tareasParaMarcarComoSincronizadas.size} tareas (probablemente eliminadas) marcadas como sincronizadas.")
        }

    }

    // --- MÉTODOS PRIVADOS DE RESOLUCIÓN DE CONFLICTOS ---
    private suspend fun handleConflicts(conflicts: List<ConflictDTO>) {
        conflicts.forEach { conflict ->
            val clientVersion = conflict.clientVersion
            val serverVersion = conflict.serverVersion
            Log.d("TareaRepository", "Resolviendo conflicto para tarea ID: ${conflict.taskId}")
            val resolvedTask = resolveConflict(clientVersion, serverVersion)
            // ESTA LÍNEA AHORA DEBERÍA FUNCIONAR
            tareaDao.insert(resolvedTask)
            Log.d("TareaRepository", "Conflicto resuelto. Versión guardada: $resolvedTask")
        }
    }

    private fun resolveConflict(client: Tarea, server: Tarea): Tarea {
        Log.d("TareaRepository", "Comparando timestamps -> Cliente: ${client.updatedAt}, Servidor: ${server.updatedAt}")
        return when {
            client.updatedAt > server.updatedAt -> client.copy(pendingSync = true)
            server.updatedAt > client.updatedAt -> server.copy(pendingSync = false)
            else -> mergeChanges(client, server)
        }
    }

    private fun mergeChanges(client: Tarea, server: Tarea): Tarea {
        Log.d("TareaRepository", "Timestamps idénticos. Fusionando cambios...")
        return server.copy(
            finalizada = client.finalizada,
            pendingSync = true
        )
    }
    // Para obtener las tareas para la papelera
    fun getDeletedTareas(): Flow<List<Tarea>> = tareaDao.getDeletedTareas()

    // Para restaurar una tarea
    suspend fun restoreTarea(tarea: Tarea) {
        // La marcamos como no borrada y pendiente de sincronizar
        val tareaRestaurada = tarea.copy(deleted = false, deletedAt = null, pendingSync = true, updatedAt = System.currentTimeMillis())
        tareaDao.update(tareaRestaurada)
    }


} // FIN DE LA CLASE