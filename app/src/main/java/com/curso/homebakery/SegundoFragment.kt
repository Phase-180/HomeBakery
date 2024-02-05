package com.curso.homebakery

import RecetaViewModel
import RecetasAdapter
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Random


class SegundoFragment : Fragment() {
    private lateinit var viewModel: RecetaViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecetasAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_segundo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(RecetaViewModel::class.java)

        val editTextNombreReceta = view.findViewById<EditText>(R.id.etNombreReceta)
        val editTextHarina = view.findViewById<EditText>(R.id.etHarina)
        val editTextHidratacion = view.findViewById<EditText>(R.id.etHidratacion)
        val editTextSal = view.findViewById<EditText>(R.id.etSal)
        val editTextMasaMadre = view.findViewById<EditText>(R.id.etMasaMadre)
        view.findViewById<Button>(R.id.btnCrearReceta).setOnClickListener {
            val nombreReceta = editTextNombreReceta.text.toString()
            val Pesoharina = editTextHarina.text.toString().toDouble()
            val hidratacion = editTextHidratacion.text.toString().toDouble()
            val sal = editTextSal.text.toString().toDouble()
            val masaMadre = editTextMasaMadre.text.toString().toDouble()
            val pesoHidratacion = Pesoharina * (hidratacion / 100)
            val pesoSal = Pesoharina * (sal / 100)
            val pesoMasaMadre = Pesoharina * (masaMadre / 100)
            // Recoge los datos de otros campos aquí

            if (true) {
                viewModel.agregarReceta(
                    Receta(
                        id = 100,
                        nombre = nombreReceta,
                        harina = Pesoharina, // Añade la lógica para recoger ingredientes
                        hidratacion = pesoHidratacion,
                        sal = pesoSal,
                        MasaMadre = pesoMasaMadre
                    )
                )

                // Construir el AlertDialog
                val builder =
                    AlertDialog.Builder(requireContext()) // Usa getContext() en una Actividad
                builder.setTitle("Mi receta")
                builder.setMessage("Ha sido creada.")

                // Agregar botones y sus acciones
                builder.setPositiveButton("Aceptar") { dialog, which ->
                    // Acción para el botón Aceptar
                }

                builder.setNegativeButton("Cancelar") { dialog, which ->
                    // Acción para el botón Cancelar
                }

                // Opcional: Configurar otros aspectos del diálogo aquí

                // Crear y mostrar el AlertDialog
                val dialog = builder.create()
                dialog.show()

            } else {
                // Muestra un mensaje de error si es necesario
            }

        }
    }
}