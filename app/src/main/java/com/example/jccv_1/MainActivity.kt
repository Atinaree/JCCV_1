package com.example.jccv_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jccv_1.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://viewnextandroid.mocklab.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

}