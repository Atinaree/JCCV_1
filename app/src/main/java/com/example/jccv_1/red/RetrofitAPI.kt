package com.example.jccv_1.red
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitAPI {
    private const val BASE_URL = "https://viewnextandroid2.wiremockapi.cloud/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
