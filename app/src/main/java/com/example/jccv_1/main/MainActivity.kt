package com.example.jccv_1.main

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
import com.example.jccv_1.main_model.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = CustomAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val apiService = RetrofitAPI.getApiService()
        apiService.getFacturas().enqueue(object : Callback<FactForm> {
            override fun onResponse(call: Call<FactForm>, response: Response<FactForm>) {
                if (response.isSuccessful) {
                    Log.d("prueba", response.body().toString())
                    val factura = response.body()
                    if (factura != null) {
                        adapter.setData(factura.facturas as ArrayList<Facturas>)
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
