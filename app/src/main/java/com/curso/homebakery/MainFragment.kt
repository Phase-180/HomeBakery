package com.curso.homebakery

import RecetaViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var viewModel: RecetaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecetasAdapter

/*    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización del ViewModel
        viewModel = ViewModelProvider(
            this,
            RecetaViewModelFactory(requireContext())
        ).get(RecetaViewModel::class.java)


        // Configuración del RecyclerView
        recyclerView =
            view.findViewById(R.id.recyclerView) // Asegurar de que el ID coincida con el layout
        adapter = RecetasAdapter(::clickBorrarReceta, ::clickVerReceta, ::clickEditarReceta)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observar los cambios en la lista de recetas
        viewModel.recetas.observe(viewLifecycleOwner) { recetas ->
            // Actualizar el adaptador con la nueva lista de recetas
            adapter.submitList(recetas.toList())
        }
    }

    private fun clickBorrarReceta(receta: Receta){
        viewModel.borrarReceta(receta)
    }

    private fun clickVerReceta(receta: Receta){
        val bundle = Bundle()
        bundle.putParcelable("receta", receta)
        bundle.putString("type", RecetaViewType.TYPE_READ_ONLY.value)
        findNavController().navigate(R.id.segundoFragment, bundle)
    }

    private fun clickEditarReceta(receta: Receta){
        val bundle = Bundle()
        bundle.putParcelable("receta", receta)
        bundle.putString("type", RecetaViewType.TYPE_UPDATE.value)
        findNavController().navigate(R.id.segundoFragment, bundle)
    }
}