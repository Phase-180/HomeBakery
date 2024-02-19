import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.curso.homebakery.AppDatabase
import com.curso.homebakery.Receta
import com.curso.homebakery.RecetaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@RecetaViewModel.HiltViewModel
class RecetaViewModel @Inject constructor(application: Context) : AndroidViewModel(application as Application) {
    constructor(app: Application) : this(application = app)

    annotation class HiltViewModel

    private val repository: RecetaRepository
    val recetas: LiveData<List<Receta>>

    init {
        val recetaDao = AppDatabase.getDatabase(application).recetaDao()
        repository = RecetaRepository(recetaDao)
        recetas = repository.todasLasRecetas
    }
    fun agregarReceta(receta: Receta) = viewModelScope.launch {
        repository.insertar(receta)
    }

    fun borrarReceta(receta: Receta) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            repository.delete(receta)
        }
    }

    fun editarReceta(receta: Receta) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            repository.update(receta)
        }
    }
}



