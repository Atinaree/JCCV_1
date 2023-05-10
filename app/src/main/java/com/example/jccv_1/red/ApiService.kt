package com.example.jccv_1.red

import com.example.jccv_1.modeladoDatos.FactForm
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    //Para coger las facturas
    @GET("facturas")
    fun getFacturas(): Call<FactForm>
}

