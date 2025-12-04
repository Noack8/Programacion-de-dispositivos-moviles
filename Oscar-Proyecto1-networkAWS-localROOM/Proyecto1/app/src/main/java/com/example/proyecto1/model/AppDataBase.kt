//Ubicación: com/example/proyecto1/model/AppDatabase.kt
package com.example.proyecto1.model
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyecto1.data.TareaDao
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Tarea::class], version =3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tareaDao(): TareaDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // 2. DEFINE LA MIGRACIÓN
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Añadimos las nuevas columnas a la tabla existente.
                // Room usa INTEGER para Boolean (0=false, 1=true) y Long.
                db.execSQL("ALTER TABLE tareas ADD COLUMN pending_sync INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE tareas ADD COLUMN updated_at INTEGER NOT NULL DEFAULT ${System.currentTimeMillis()}")
                db.execSQL("ALTER TABLE tareas ADD COLUMN deleted INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE tareas ADD COLUMN deletedAt INTEGER")
            }
        }
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    // 3. AÑADE LA MIGRACIÓN AL CONSTRUCTOR DE LA BD
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
