package com.example.jccv_1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jccv_1.R
import com.example.jccv_1.red.RetrofitToRoom
import com.example.jccv_1.databinding.ActivityMainBinding
import com.example.jccv_1.red.RetrofitAPI
import com.example.jccv_1.database.facturasAPP.Companion.room
import com.example.jccv_1.modeladoDatos.CustomAdapter
import com.example.jccv_1.modeladoDatos.FactForm
import com.example.jccv_1.modeladoDatos.Facturas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
     lateinit var binding: ActivityMainBinding
     val adapter = CustomAdapter()
     var facturasList: ArrayList<Facturas> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            startActivity(intent)

        lifecycleScope.launch {
            val facturas = room.facturaDAO().getALL()
            Log.d("prueba","onCreate: ${facturas.size} facturas")

                    }
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
}




