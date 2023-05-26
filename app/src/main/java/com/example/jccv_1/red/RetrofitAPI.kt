package com.example.jccv_1.red

import co.infinum.retromock.Retromock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitAPI {

    //parte para el retrofit
    private const val BASE_URL = "https://viewnextandroid.mocklab.io/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    // Parte para el mock
    var retromock = Retromock.Builder()
        .retrofit(retrofit)
        .build()

    fun getMockService(): MockService {
        return retromock.create(MockService::class.java)
    }

}
