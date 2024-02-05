import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.curso.homebakery.R
import com.curso.homebakery.Receta

class RecetasAdapter : ListAdapter<Receta, RecetasAdapter.RecetaViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Receta>() {
        override fun areItemsTheSame(oldItem: Receta, newItem: Receta): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Receta, newItem: Receta): Boolean {
            return oldItem == newItem
        }
    }

    class RecetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(receta: Receta) {
            // Realiza el binding de los datos de la receta con tu vista
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        return RecetaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_receta, parent, false))
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = getItem(position)
        holder.bind(receta)
    }
}
