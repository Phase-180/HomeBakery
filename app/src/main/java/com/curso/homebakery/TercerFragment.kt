package com.curso.homebakery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.curso.homebakery.databinding.FragmentTercerBinding

class TercerFragment : Fragment() {

    private var _binding: FragmentTercerBinding? = null
    // Esta propiedad es solo válida entre onCreateView y onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento utilizando View Binding
        _binding = FragmentTercerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Aquí puedes acceder a las vistas usando binding\

        binding.cardRecycleResult.visibility = View.GONE
        binding.btnCalcular.setOnClickListener {


            val horasFermentacion = binding.etHorasFermentacion.text.toString() // Puedes ajustar este valor para probar diferentes rangos
            val proporcionMasaMadre = calcularProporcionMasaMadre(horasFermentacion.toInt())
            val cantidadMasaMadre = binding.etCantidadMasaMadre.text.toString().toInt()
            val pieMasaMadre = (cantidadMasaMadre * proporcionMasaMadre)/ 100
            val cantidadAguaHarina = (cantidadMasaMadre - pieMasaMadre)/2

            binding.tvTitulo.setText("Resultado")
            binding.cardRecycle.visibility = View.GONE
            binding.cardRecycleResult.visibility = View.VISIBLE
            binding.etPieMasaMadre.isFocusableInTouchMode = false
            binding.etHarinaTotal.isFocusableInTouchMode = false
            binding.etAguaTotal.isFocusableInTouchMode = false
            binding.etPieMasaMadre.setText(pieMasaMadre.toString() + "gr")
            binding.etHarinaTotal.setText(cantidadAguaHarina.toString() + "gr")
            binding.etAguaTotal.setText(cantidadAguaHarina.toString() + "gr")
        }



    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Limpiar la referencia a binding cuando la vista se destruya
        _binding = null
    }

    fun calcularProporcionMasaMadre(horasFermentacion: Int): Double {
        return when (horasFermentacion) {
            in 0..5 -> {
                // Si la fermentación es menos de 6 horas, aumenta la proporción de masa madre
                50.0 // Porcentaje de masa madre sugerido para fermentaciones rápidas
            }
            in 6..8 -> {
                // Para fermentación de 6 a 8 horas
                20.0 // Porcentaje de masa madre sugerido para fermentaciones medias
            }
            in 9..12 -> {
                // Si decides alargar las horas de fermentación, reduce la proporción de masa madre
                10.0 // Porcentaje de masa madre sugerido para fermentaciones más largas
            }
            else -> {
                // Para fermentaciones aún más largas, o fuera del rango esperado
                5.0 // Mínimo porcentaje de masa madre sugerido para fermentaciones muy largas
            }
        }
    }

}