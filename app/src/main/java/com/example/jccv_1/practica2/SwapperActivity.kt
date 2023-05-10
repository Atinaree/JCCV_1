package com.example.jccv_1.practica2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jccv_1.activities.MainActivity
import com.example.jccv_1.databinding.SwapperBinding
import com.example.jccv_1.red.RetrofitToRoom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SwapperActivity: AppCompatActivity() {
    private lateinit var binding: SwapperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SwapperBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Cargamos datos en la base de datos.
        GlobalScope.launch {
            val retrofitToRoom = RetrofitToRoom(application)
            retrofitToRoom.getMyData()
        }
    }
}
