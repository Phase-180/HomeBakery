package com.curso.homebakery

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.curso.homebakery.AppDatabase
import com.curso.homebakery.Receta
import com.curso.homebakery.RecetaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@CategoriaViewModel.HiltViewModel
class CategoriaViewModel @Inject constructor(application: Context) :
    AndroidViewModel(application as Application) {
    constructor(app: Application) : this(application = app)

    annotation class HiltViewModel

    private val _category = MutableStateFlow<Categoria?>(null)
    val category: StateFlow<Categoria?> = _category
    private val repository: CategoriaRepository
    val categorias: LiveData<List<Categoria>>

    init {
        val categoriaDao = AppDatabase.getDatabase(application).categoriaDao()
        repository = CategoriaRepository(categoriaDao)
        categorias = repository.todasLasCategorias
    }

    fun agregarCategoria(categoria: Categoria) = viewModelScope.launch {
        repository.insertar(categoria)
    }

    fun borrarCategoria(categoria: Categoria) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.delete(categoria)
        }
    }

    fun editarCategoria(categoria: Categoria) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.update(categoria)
        }
    }


    fun getCategoryById(id: Int) {
        viewModelScope.launch {
            _category.value = repository.getCategoryById(id)
        }
    }
}



