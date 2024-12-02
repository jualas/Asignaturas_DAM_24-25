package es.jualas.carteles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // ViewHolder interno que contiene las vistas de cada elemento
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePoster: ImageView = itemView.findViewById(R.id.imagePoster) // Imagen del cartel
        val textTitle: TextView = itemView.findViewById(R.id.textTitle) // Título del cartel
        val textYear: TextView = itemView.findViewById(R.id.textYear) // Año de lanzamiento del cartel
        val textCondition: TextView = itemView.findViewById(R.id.textCondition) // Estado de conservación del cartel
        val textPrice: TextView = itemView.findViewById(R.id.textPrice) // Precio estimado del cartel
    }

    // Crea una nueva vista para cada elemento de la lista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
        return ItemViewHolder(view)
    }

    // Vincula los datos del elemento a las vistas del ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.textTitle.text = item.titulo // Asigna el título del cartel
        holder.textYear.text = "Año: ${item.anoLanzamiento}" // Asigna el año de lanzamiento
        holder.textCondition.text = "Estado: ${item.estadoConservacion}" // Asigna el estado de conservación
        holder.textPrice.text = "Precio estimado: $${item.precioEstimado}" // Asigna el precio estimado
        // Asigna imagen a holder.imagePoster si es necesario
    }

    // Devuelve el número total de elementos en la lista
    override fun getItemCount(): Int = items.size
}