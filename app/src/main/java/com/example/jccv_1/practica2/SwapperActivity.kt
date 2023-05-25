package com.example.jccv_1.practica2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jccv_1.R
import com.example.jccv_1.activities.MainActivity
import com.example.jccv_1.databinding.SwapperBinding
import com.example.jccv_1.practica3.LoginActivity
import com.example.jccv_1.red.RetrofitToRoom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SwapperActivity: AppCompatActivity() {
    private lateinit var binding: SwapperBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SwapperBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle: Bundle? = intent.extras
        val email = bundle?.getString("email")
        binding.correo.text = email
        binding.retromoco.text = "Retrofit Activo"
        binding.retromoco.setOnClickListener(){

        if (RetrofitToRoom.variabledepaso == 0) {
            binding.retromoco.text = "RetroMock Activo"
            binding.retromoco.setBackgroundColor(getColor(R.color.rojo))
            RetrofitToRoom.variabledepaso = 1
            // Cargamos datos en la base de datos.
            GlobalScope.launch {
                val retrofitToRoom = RetrofitToRoom(application)
                retrofitToRoom.getMyData()
            }
        }else{
            binding.retromoco.text = "RetroFit Activo"
            binding.retromoco.setBackgroundColor(getColor(R.color.verde))
            RetrofitToRoom.variabledepaso = 0
            // Cargamos datos en la base de datos.
            GlobalScope.launch {
                val retrofitToRoom = RetrofitToRoom(application)
                retrofitToRoom.getMyData()
            }
        }

        }


        // Configurar el click listener para el botón con ID button5
        binding.button5.setOnClickListener() {
            // Crear un intent para abrir la actividad MainActivity
            val intent = Intent(applicationContext, MainActivity::class.java)
            // Iniciar la actividad MainActivity
            startActivity(intent)
        }

        // Lo mismo que el de arriba pero para otro activity
        binding.button6.setOnClickListener(){
            val intent = Intent(applicationContext, Actividad2::class.java)
            startActivity(intent)
        }

        binding.button8.setOnClickListener(){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            // Iniciar la actividad MainActivity
            startActivity(intent)

        }


    }
}
