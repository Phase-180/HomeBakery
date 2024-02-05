package com.curso.homebakery

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecetaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: Receta)

    @Query("SELECT * FROM receta")
    fun getAll(): LiveData<List<Receta>>
}
