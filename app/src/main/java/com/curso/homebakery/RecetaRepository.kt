package com.curso.homebakery

import androidx.lifecycle.LiveData

class RecetaRepository(private val recetaDao: RecetaDao) {

    val todasLasRecetas: LiveData<List<Receta>> = recetaDao.getAll()

    suspend fun insertar(receta: Receta) {
        recetaDao.insert(receta)
    }

    suspend fun update(receta: Receta) {
        recetaDao.update(receta)
    }

    suspend fun delete(receta: Receta) {
        recetaDao.delete(receta)
    }


}
