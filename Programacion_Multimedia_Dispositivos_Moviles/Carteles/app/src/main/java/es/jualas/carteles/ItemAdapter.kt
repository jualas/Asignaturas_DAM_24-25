package es.jualas.carteles

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // ViewHolder interno que contiene las vistas de cada elemento
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePoster: ImageView = itemView.findViewById(R.id.imagePoster)
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textYear: TextView = itemView.findViewById(R.id.textYear)
        val textCondition: TextView = itemView.findViewById(R.id.textCondition)
        val textPrice: TextView = itemView.findViewById(R.id.textPrice)
    }

    // Crea nuevas vistas (invocado por el layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        return ItemViewHolder(view)
    }

    // Reemplaza el contenido de una vista (invocado por el layout manager)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        holder.textTitle.text = item.titulo
        holder.textYear.text = "${context.getString(R.string.year)}: ${item.anoLanzamiento}"
        holder.textCondition.text = "${context.getString(R.string.condition)}: ${getTranslatedCondition(context, item.estadoConservacion)}"
        holder.textPrice.text = "${context.getString(R.string.estimated_price)}: $${item.precioEstimado}"
    }

    // Traduce el estado de conservación del artículo
    private fun getTranslatedCondition(context: Context, condition: String): String {
        return when (condition) {
            "Excelente" -> context.getString(R.string.condition_excellent)
            "Bueno" -> context.getString(R.string.condition_good)
            "Regular" -> context.getString(R.string.condition_regular)
            else -> condition
        }
    }

    // Devuelve el tamaño del dataset (invocado por el layout manager)
    override fun getItemCount(): Int = items.size
}