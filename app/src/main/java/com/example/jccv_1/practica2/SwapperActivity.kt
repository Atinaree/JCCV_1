package com.example.jccv_1.practica2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jccv_1.activities.MainActivity
import com.example.jccv_1.databinding.SwapperBinding


class SwapperActivity: AppCompatActivity() {
    private lateinit var binding: SwapperBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SwapperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button5.setOnClickListener() {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }

        binding.button6.setOnClickListener(){
                val intent = Intent(applicationContext, Actividad2::class.java)
                startActivity(intent)


        }

    }

}