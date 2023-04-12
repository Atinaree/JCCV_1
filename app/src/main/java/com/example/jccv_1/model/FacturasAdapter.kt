package com.example.jccv_1.model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jccv_1.R

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val fechas = arrayOf("hola", "pepe", "carlos")
    private val estados = arrayOf("paco", "susana", "sofia")
    private val importes = arrayOf("1", "2", "3")
    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemfactura, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemFecha.text = fechas[position]
        holder.itemEstado.text = estados[position]
        holder.itemImporte.text = importes[position]

        // Agregar escuchador de clics en la cardview
        holder.itemView.setOnClickListener {
            // Crear una instancia de PopupWindow
            val popup = PopupWindow(holder.itemView.context)
            // Configurar la vista emergente con un archivo de diseño
            val popupView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.popup, null)
            popup.contentView = popupView
            // Establecer las dimensiones de la vista emergente
            popup.width = ViewGroup.LayoutParams.WRAP_CONTENT
            popup.height = ViewGroup.LayoutParams.WRAP_CONTENT

            // Agregar escuchador de clics en el botón de cerrar
            val closeButton = popupView.findViewById<Button>(R.id.close_button)
            closeButton.setOnClickListener {
                // Cerrar la vista emergente
                popup.dismiss()
            }

            // Mostrar la vista emergente en la posición de la cardview
            popup.showAsDropDown(holder.itemView)

        }
    }

    override fun getItemCount(): Int {
        return fechas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemFecha: TextView = itemView.findViewById(R.id.fecha)
        val itemEstado: TextView = itemView.findViewById(R.id.estado)
        val itemImporte: TextView = itemView.findViewById(R.id.importe)
    }

}
