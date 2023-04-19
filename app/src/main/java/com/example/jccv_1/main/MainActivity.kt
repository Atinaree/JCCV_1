package com.example.jccv_1.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    private var facturasList: ArrayList<Facturas> = ArrayList()
    private var facturasListSaved: ArrayList<Facturas> = ArrayList()
    private var isResumed = false

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
                        //Inicializamos la variable que cargara el recyclerView y le damos el contenido del retrofit
                        facturasList = factura.facturas as ArrayList<Facturas>
                        //Inicializamos una variable para guardar una copia de la respuesta, pero que no modificaremos
                        facturasListSaved = factura.facturas as ArrayList<Facturas>
                        adapter.setData(facturasList)
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

    override fun onResume() {
        super.onResume()

        if (isResumed) {
            Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show()
            adapter.setData(facturasList)
        }

        isResumed = true
    }





}
