package com.example.jccv_1.main_model
import retrofit2.Call
import retrofit2.http.GET
interface ApiService {
        @GET("/facturas")
        fun getFacturas(): Call<FactForm>
}