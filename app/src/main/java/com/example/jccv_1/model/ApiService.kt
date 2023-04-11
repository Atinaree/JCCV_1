package com.example.jccv_1.model

import retrofit2.Response
import retrofit2.http.Url
import retrofit2.http.GET



interface ApiService {
@GET
fun getFacturasModelByFecha(@Url url:String):Response<Facturas>
}