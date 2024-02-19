package com.curso.homebakery

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class CategoriaRepository(private val categoriaDao: CategoriaDao) {

    val todasLasCategorias: LiveData<List<Categoria>> = categoriaDao.getAll()

    suspend fun getCategoryById(id: Int): Categoria? {
        return categoriaDao.getById(id)
    }

    suspend fun insertar(categoria: Categoria) {
        categoriaDao.insert(categoria)
    }

    suspend fun update(categoria: Categoria) {
        categoriaDao.update(categoria)
    }

    suspend fun delete(categoria: Categoria) {
        categoriaDao.delete(categoria)
    }


}
