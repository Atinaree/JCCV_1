package com.example.jccv_1.secondary
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.jccv_1.R
import com.example.jccv_1.databinding.SecondaryActivityBinding
import com.example.jccv_1.main_model.CustomAdapter


class SecondaryActivity : Activity() {
    lateinit var binding: SecondaryActivityBinding
    lateinit var itemfiltross: LinearLayout
    lateinit var botonaplicar: Button
    lateinit var adapter: CustomAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondaryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Cargar layout en el LinearLayout
        val inflater = LayoutInflater.from(this)
        itemfiltross = binding.itemfiltross
        val item = inflater.inflate(R.layout.itemfiltross, null)
        itemfiltross.addView(item)


        botonaplicar = binding.button



        adapter = CustomAdapter()
        botonaplicar.setOnClickListener {
            adapter.filtrarPorImporte(12.84)



            Toast.makeText(this, "2323", Toast.LENGTH_SHORT).show()


        }

        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            finish()
    }





}}
