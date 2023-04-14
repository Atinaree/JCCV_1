package com.example.jccv_1.model

import com.google.gson.annotations.SerializedName

data class Facturas(

    var descEstado: String,
    var importeOrdenacion: Double,
    val fecha: String)
