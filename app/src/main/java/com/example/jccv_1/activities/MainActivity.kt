package com.example.jccv_1.activities
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jccv_1.R
import com.example.jccv_1.activities.SecondaryActivity.Companion.anuladas
import com.example.jccv_1.activities.SecondaryActivity.Companion.cfija

import com.example.jccv_1.activities.SecondaryActivity.Companion.fecha
import com.example.jccv_1.activities.SecondaryActivity.Companion.fecha2
import com.example.jccv_1.activities.SecondaryActivity.Companion.importe
import com.example.jccv_1.activities.SecondaryActivity.Companion.pagadas
import com.example.jccv_1.activities.SecondaryActivity.Companion.pendientes
import com.example.jccv_1.activities.SecondaryActivity.Companion.plan
import com.example.jccv_1.red.RetrofitToRoom
import com.example.jccv_1.databinding.ActivityMainBinding
import com.example.jccv_1.modeladoDatos.CustomAdapter
import com.example.jccv_1.modeladoDatos.Facturas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.jar.Attributes.Name

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = CustomAdapter()
    private val secondaryLauncher = registerForActivityResult(StartActivityForResult()){ activityResult ->
        if(activityResult.resultCode == RESULT_OK){

            val fecha1 = activityResult.data?.getStringExtra(fecha).orEmpty()
            val fecha2 = activityResult.data?.getStringExtra(fecha2).orEmpty()
            val importe = activityResult.data?.getStringExtra(importe).orEmpty()
            val pagadas = activityResult.data?.getStringExtra(pagadas).orEmpty()
            val anuladas = activityResult.data?.getStringExtra(anuladas).orEmpty()
            val cfija = activityResult.data?.getStringExtra(cfija).orEmpty()
            val pendientes = activityResult.data?.getStringExtra(pendientes).orEmpty()
            val plan = activityResult.data?.getStringExtra(plan).orEmpty()




            Log.d("hii",pagadas + pendientes)



        }else{
            Toast.makeText(this, "Result not OK", Toast.LENGTH_SHORT).show()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofitToRoom = RetrofitToRoom(application)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            val myDataList = withContext(Dispatchers.IO) {
                retrofitToRoom.getMyData()
            }
            adapter.setData(myDataList as ArrayList<Facturas>)
        }
        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            secondaryLauncher.launch(intent)
        }
    }





}









