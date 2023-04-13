package com.example.jccv_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jccv_1.databinding.ActivityMainBinding
import com.example.jccv_1.model.ApiService
import com.example.jccv_1.model.CustomAdapter
import com.example.jccv_1.model.Facturas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = CustomAdapter()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://viewnextandroid.mocklab.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
            api.getFacturas().enqueue(object : Callback<List<Facturas>>{
                override fun onResponse(
                    call: Call<List<Facturas>>,
                    response: Response<List<Facturas>>
                ) {

                }

                override fun onFailure(call: Call<List<Facturas>>, t: Throwable) {

                }
            })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }

        }



