package com.curso.homebakery

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecetaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: Receta)

    @Query("SELECT * FROM receta")
    fun getAll(): LiveData<List<Receta>>

    @Update
    fun update(receta: Receta)

    @Delete
    fun delete(receta: Receta)

    @Query("SELECT * FROM categorias")
    fun getAllCategorias(): LiveData<List<Categoria>>

    @Query("SELECT * FROM receta WHERE categoriaId = :categoriaId")
    fun getRecetasByCategoria(categoriaId: Int): LiveData<List<Receta>>
}
