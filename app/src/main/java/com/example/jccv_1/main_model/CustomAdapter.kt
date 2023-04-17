package com.example.jccv_1.main_model
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jccv_1.R

class CustomAdapter() : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
     var facturasList = ArrayList<Facturas>()
     var isPopupOpen = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemfactura, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return facturasList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemFecha: TextView = itemView.findViewById(R.id.fecha)
        val itemEstado: TextView = itemView.findViewById(R.id.estado)
        val itemImporte: TextView = itemView.findViewById(R.id.importe)
    }
    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemFecha.text = facturasList[position].fecha
        holder.itemEstado.text = facturasList[position].descEstado
        holder.itemImporte.text = facturasList[position].importeOrdenacion.toString()
        holder.itemView.setOnClickListener {
            if (!isPopupOpen) {
                isPopupOpen = true
                val popup = PopupWindow(holder.itemView.context)
                val popupView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.popup, null)
                popup.contentView = popupView
                popup.width = ViewGroup.LayoutParams.WRAP_CONTENT
                popup.height = ViewGroup.LayoutParams.WRAP_CONTENT
                popup.showAtLocation(popupView,1,0,0)

                val closeButton = popupView.findViewById<Button>(R.id.close_button)
                closeButton.setOnClickListener {
                    popup.dismiss()
                    isPopupOpen = false
                }
                popup.showAsDropDown(holder.itemView)
            }
        }
    }
    fun setData(facturas: ArrayList<Facturas>) {
        facturasList = facturas
        notifyDataSetChanged()
    }

    fun filtrarPorEstado(estado: String) {
        val facturasFiltradas = ArrayList<Facturas>()
        for (factura in facturasList) {
            if (factura.descEstado == estado) {
                facturasFiltradas.add(factura)
            }
        }
        setData(facturasFiltradas)

    }

    fun filtrarPorFecha(fecha: String) {
        val facturasFiltradas = ArrayList<Facturas>()
        for (factura in facturasList) {
            if (factura.fecha == fecha) {
                facturasFiltradas.add(factura)
            }
        }
        setData(facturasFiltradas)
    }

    fun filtrarPorImporte(importe: Double) {
        val facturasFiltradas = ArrayList<Facturas>()
        for (factura in facturasList) {
            if (factura.importeOrdenacion == importe) {
                facturasFiltradas.add(factura)
            }
        }
        setData(facturasFiltradas)
    }


}
