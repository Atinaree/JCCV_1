package com.example.jccv_1.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.jccv_1.R

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder> (){
    val fechas = arrayOf("hola","pepe","carlos")
    val estados = arrayOf("paco", "susana", "sofia")
    val importes = arrayOf("1","2", "3")


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.itemfactura, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        viewHolder.itemFecha.text = fechas[i]
        viewHolder.itemEstado.text = estados[i]
        viewHolder.itemImporte.text = importes[i]
    }

    override fun getItemCount(): Int {
    return fechas.size
    }
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var itemFecha: TextView
        var itemEstado: TextView
        var itemImporte: TextView
            init {
                itemFecha = itemView.findViewById(R.id.fecha)
                itemEstado = itemView.findViewById(R.id.estado)
                itemImporte = itemView.findViewById(R.id.importe)
            }

    }


}