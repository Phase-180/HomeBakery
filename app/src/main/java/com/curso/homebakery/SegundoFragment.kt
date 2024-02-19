package com.curso.homebakery

import RecetaViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job

class SegundoFragment : Fragment() {
    private lateinit var viewModel: RecetaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecetasAdapter
    private lateinit var categoriaviewModel: CategoriaViewModel
    private var categoriasList: List<Categoria> = listOf()
    private var selectedCategoriaId: Int = -1
    private lateinit var viewType: RecetaViewType
    private var receta: Receta? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_segundo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        receta = arguments?.getParcelable("receta")
        viewType = arguments?.getString("type")?.let {
            RecetaViewType.fromString(it)
        } ?: RecetaViewType.TYPE_CREATE

        viewModel = ViewModelProvider(requireActivity()).get(RecetaViewModel::class.java)
        categoriaviewModel =
            ViewModelProvider(requireActivity()).get(CategoriaViewModel::class.java)

        val spinnerCategorias: Spinner = view.findViewById(R.id.spinnerCategorias)

        // Observa las categorías y actualiza el Spinner cuando los datos estén disponibles
        categoriaviewModel.categorias.observe(viewLifecycleOwner, { categorias ->
            categoriasList = categorias
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categorias.map { it.nombre })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategorias.adapter = adapter
        })
        val editTextNombreReceta = view.findViewById<EditText>(R.id.etNombreReceta)
        val editTextHarina = view.findViewById<EditText>(R.id.etHarina)
        val editTextHidratacion = view.findViewById<EditText>(R.id.etHidratacion)
        val editTextSal = view.findViewById<EditText>(R.id.etSal)
        val editTextMasaMadre = view.findViewById<EditText>(R.id.etMasaMadre)
        val editTextCategoria = view.findViewById<EditText>(R.id.etCategoria)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val btnCrearReceta = view.findViewById<Button>(R.id.btnCrearReceta)


        if (receta != null) {

            val hidratacion = receta!!.hidratacion
            val sal = receta!!.sal
            val masaMadre = receta!!.MasaMadre
            val categoriaIdReceta = receta!!.categoriaId
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                categoriaviewModel.category.collect { categoria ->
                    if (categoria != null && categoriaIdReceta == categoria.categoriaId) {
                        editTextCategoria.setText(categoria.nombre)
                    }
                }
            }

            if (viewType == RecetaViewType.TYPE_UPDATE) {
                editTextNombreReceta.setText(receta!!.nombre)
                editTextHarina.setText(receta!!.harina.toString())
                editTextHidratacion.setText(hidratacion.toString())
                editTextSal.setText(sal.toString())
                editTextMasaMadre.setText(masaMadre.toString())
                categoriaviewModel.getCategoryById(categoriaIdReceta)
            } else {
                editTextNombreReceta.setText(receta!!.nombre)
                editTextHarina.setText(receta!!.harina.toString() + " gr")
                editTextHidratacion.setText(hidratacion.toString() + " gr")
                editTextSal.setText(sal.toString() + " gr")
                editTextMasaMadre.setText(masaMadre.toString() + " gr")
                categoriaviewModel.getCategoryById(categoriaIdReceta)
            }
        }

        when (viewType) {
            RecetaViewType.TYPE_UPDATE -> {
                editTextCategoria.visibility = View.GONE
                tvTitle.text = "Editar receta"
                btnCrearReceta.text = "Editar receta"
            }

            RecetaViewType.TYPE_READ_ONLY -> {
                tvTitle.setText("Ver receta")
                btnCrearReceta.visibility = View.INVISIBLE
                spinnerCategorias.visibility = View.INVISIBLE
                editTextNombreReceta.isFocusable = false
                editTextHarina.isFocusable = false
                editTextHidratacion.isFocusable = false
                editTextSal.isFocusable = false
                editTextMasaMadre.isFocusable = false
                editTextCategoria.isFocusable = false
            }

            RecetaViewType.TYPE_CREATE -> {
                editTextCategoria.visibility = View.GONE
            }
        }
        spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Asegúrate de que la lista de categorías no esté vacía y que la posición sea válida
                if (categoriasList.isNotEmpty() && position != AdapterView.INVALID_POSITION) {
                    selectedCategoriaId = categoriasList[position].categoriaId

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        btnCrearReceta.setOnClickListener {
            val nombreReceta = editTextNombreReceta.text.toString()
            val Pesoharina = editTextHarina.text.toString().toDouble()
            val hidratacion = editTextHidratacion.text.toString().toDouble()
            val sal = editTextSal.text.toString().toDouble()
            val masaMadre = editTextMasaMadre.text.toString().toDouble()
            val pesoHidratacion = (Pesoharina * hidratacion) / 100
            val pesoSal = (Pesoharina * sal) / 100
            val pesoMasaMadre = (Pesoharina * masaMadre) / 100


            // Utilizando los datos calculados para la creacion del objeto, que lo utilizaremos para crear una nueva receta o actualizar
            var recetaNueva = Receta(
                id = receta?.id ?: 0,
                nombre = nombreReceta,
                harina = Pesoharina,
                hidratacion = pesoHidratacion,
                sal = pesoSal,
                MasaMadre = pesoMasaMadre,
                categoriaId = selectedCategoriaId
            )



            if (true) {

                if (viewType == RecetaViewType.TYPE_UPDATE) {
                    viewModel.editarReceta(recetaNueva)
                } else {
                    viewModel.agregarReceta(recetaNueva)
                }

                // Construir el AlertDialog
                val builder =
                    AlertDialog.Builder(requireContext()) // Usa getContext() en una Actividad
                builder.setTitle("Mi receta")
                if (viewType == RecetaViewType.TYPE_UPDATE) {
                    builder.setMessage("Ha sido editada")
                } else {
                    builder.setMessage("Ha sido creada")
                }

                // Agregar botones y sus acciones
                builder.setPositiveButton("Aceptar") { dialog, which ->
                    // Una vez aceptado utilizo la funcion goBack() para que me lleve al fragment principal
                    goBack()
                }
                // Podia haber utilizado tambien el boton cancelar, pero no le veia utilidad
//                builder.setNegativeButton("Cancelar") { dialog, which ->
//                    // Acción para el botón Cancelar
//
//                }


                // Crear y mostrar el AlertDialog
                val dialog = builder.create()
                dialog.show()

            } else {
                // Muestra un mensaje de error si es necesario
            }

        }
    }


    private fun goBack() {
        fragmentManager?.let {
            if (it.backStackEntryCount > 0) {
                it.popBackStack()
            }
        }
    }
}
