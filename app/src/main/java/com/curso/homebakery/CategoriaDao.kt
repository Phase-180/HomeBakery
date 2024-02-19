package com.curso.homebakery

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface CategoriaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(categoria: Categoria)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(categories: List<Categoria>)

    @Query("SELECT * FROM categorias")
    fun getAll(): LiveData<List<Categoria>>

    @Query("SELECT * FROM categorias WHERE categoriaId = :id")
    suspend fun getById(id: Int): Categoria?
    @Update
    fun update(categoria: Categoria)

    @Delete
    fun delete(categoria: Categoria)


}
