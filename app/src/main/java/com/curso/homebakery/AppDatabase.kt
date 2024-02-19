package com.curso.homebakery

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(entities = [Categoria::class, Receta::class], version = 3)
abstract class AppDatabase : RoomDatabase() {


    abstract fun recetaDao(): RecetaDao
    abstract fun categoriaDao(): CategoriaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database"
                ).addCallback(roomCallback)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch {
                    // Obtén la instancia de la base de datos y luego el DAO para insertar categorías
                    val dao = INSTANCE?.categoriaDao()
                    val categorias = listOf(
                        Categoria(nombre = "Banneton"),
                        Categoria(nombre = "Molde"),
                        Categoria(nombre = "Alta Hidratación")

                    )
                    dao?.insertAll(categorias)
                }
            }
        }
    }
}