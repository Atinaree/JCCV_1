package com.example.jccv_1.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jccv_1.R
import com.example.jccv_1.databinding.ActivityMainBinding
import com.example.jccv_1.main_model.*
import com.example.jccv_1.secondary.SecondaryActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

     lateinit var binding: ActivityMainBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            startActivity(intent)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = CustomAdapter()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://viewnextandroid.wiremockapi.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

                retrofit.create(ApiService::class.java).getFacturas().enqueue(object : Callback<FactForm> {
                    override fun onResponse(call: Call<FactForm>, response: Response<FactForm>) {
                        if (response.isSuccessful) {
                            Log.d("prueba", response.body().toString())
                            val factura = response.body()
                            if (factura != null) {
                                adapter.setData(factura.facturas as ArrayList<Facturas>) // convierte el objeto JSON en una lista para adaptarlo al RecyclerView
                                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                                recyclerView.adapter = adapter
                            }
                        } else {
                            // Manejar el error y mostrar un mensaje de error al usuario
                        }
                    }
                    override fun onFailure(call: Call<FactForm>, t: Throwable) {
                        // Manejar el error y mostrar un mensaje de error al usuario
                    }
                })
    }




}
