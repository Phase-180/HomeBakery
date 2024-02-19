package com.curso.homebakery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RecetasAdapter(
    private val clickBorrarReceta: (Receta) -> Unit,
    private val clickVerReceta: (Receta) -> Unit,
    private val clickEditarReceta: (Receta) -> Unit
) : ListAdapter<Receta, RecetasAdapter.RecetaViewHolder>(DiffCallback) {


    companion object DiffCallback : DiffUtil.ItemCallback<Receta>() {
        override fun areItemsTheSame(oldItem: Receta, newItem: Receta): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Receta, newItem: Receta): Boolean {
            return oldItem == newItem
        }
    }

    class RecetaViewHolder(
        itemView: View,
        private val clickBorrarReceta: (Receta) -> Unit,
        private val clickVerReceta: (Receta) -> Unit,
        private val clickEditarReceta: (Receta) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(receta: Receta) {
            itemView.findViewById<TextView>(R.id.tvNombreReceta).text = receta.nombre
            itemView.findViewById<ImageView>(R.id.btnBorrar).setOnClickListener { clickBorrarReceta.invoke(receta) }
            itemView.findViewById<ImageView>(R.id.btnEditar).setOnClickListener { clickEditarReceta.invoke(receta) }
            itemView.setOnClickListener { clickVerReceta.invoke(receta) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        return RecetaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_receta, parent, false), clickBorrarReceta, clickVerReceta, clickEditarReceta)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = getItem(position)
        holder.bind(receta)
    }
}
