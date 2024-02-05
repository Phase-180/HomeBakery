package com.curso.homebakery

import androidx.lifecycle.LiveData

class RecetaRepository(private val recetaDao: RecetaDao) {

    val todasLasRecetas: LiveData<List<Receta>> = recetaDao.getAll()

    suspend fun insertar(receta: Receta) {
        recetaDao.insert(receta)
    }

    // Puedes agregar más métodos según sea necesario, como update, delete, etc.
}
