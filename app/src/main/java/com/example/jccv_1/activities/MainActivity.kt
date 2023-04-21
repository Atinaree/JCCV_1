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
import com.example.jccv_1.modeladoDatos.CustomAdapter
import com.example.jccv_1.modeladoDatos.Facturas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = CustomAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofitToRoom = RetrofitToRoom(application)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            val myDataList = withContext(Dispatchers.IO) {
                retrofitToRoom.getMyData()
            }
            adapter.setData(myDataList as ArrayList<Facturas>)
        }
    }
}



