package com.example.jccv_1.model

import retrofit2.Call
import retrofit2.http.GET



interface FacturasApi {

        @GET("facturas")
        fun getFacturas(): Call<List<Facturas>>

}