package com.example.jccv_1.modeladoDatos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jccv_1.R

class CustomAdapter() : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // Lista de facturas
    var facturasList = ArrayList<Facturas>()

    // Variable para controlar si el popup está abierto o no
    var isPopupOpen = false

    // Crea y devuelve una nueva instancia de ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Infla el diseño del elemento de factura
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemfactura, parent, false)
        return ViewHolder(view)
    }

    // Devuelve el número de elementos en la lista de facturas
    override fun getItemCount(): Int {
        return facturasList.size
    }

    // Clase ViewHolder que contiene las vistas de un elemento de la lista
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemFecha: TextView = itemView.findViewById(R.id.fecha)
        val itemEstado: TextView = itemView.findViewById(R.id.estado)
        val itemImporte: TextView = itemView.findViewById(R.id.importe)
    }

    // Vincula los datos de la lista de facturas con las vistas en un ViewHolder
    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Obtiene los datos de la factura en la posición actual
        val factura = facturasList[position]

        // Establece los datos en las vistas correspondientes
        holder.itemFecha.text = factura.fecha
        holder.itemEstado.text = factura.descEstado
        holder.itemImporte.text = factura.importeOrdenacion.toString()

        // Configura el clic en el elemento de la lista
        holder.itemView.setOnClickListener {
            if (!isPopupOpen) {
                isPopupOpen = true

                // Crea una nueva instancia de PopupWindow
                val popup = PopupWindow(holder.itemView.context)

                // Infla el diseño del popup
                val popupView =
                    LayoutInflater.from(holder.itemView.context).inflate(R.layout.popup, null)

                // Establece el contenido del popup
                popup.contentView = popupView

                // Configura el tamaño del popup
                popup.width = ViewGroup.LayoutParams.WRAP_CONTENT
                popup.height = ViewGroup.LayoutParams.WRAP_CONTENT

                // Muestra el popup en la ubicación especificada
                popup.showAtLocation(popupView, 1, 0, 0)

                // Obtiene el botón de cierre del popup
                val closeButton = popupView.findViewById<Button>(R.id.close_button)

                // Configura el clic en el botón de cierre
                closeButton.setOnClickListener {
                    // Cierra el popup
                    popup.dismiss()

                    // Actualiza el estado de la variable isPopupOpen
                    isPopupOpen = false
                }

                // Muestra el popup como un desplegable del elemento de la lista
                popup.showAsDropDown(holder.itemView)
            }
        }
    }

    // Actualiza los datos de la lista de facturas y notifica los cambios
    fun setData(facturas: ArrayList<Facturas>) {
        facturasList = facturas
        notifyDataSetChanged()
    }
}
