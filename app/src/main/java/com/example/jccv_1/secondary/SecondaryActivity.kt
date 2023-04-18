package com.example.jccv_1.secondary
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.SeekBar
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




            Toast.makeText(this, "2323", Toast.LENGTH_SHORT).show()


        }

        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            finish()



    }
// Boton para quitar filtros
        binding.button3.setOnClickListener {


            val slider = findViewById<SeekBar>(R.id.barraImporte)
            val checkBox1 = findViewById<CheckBox>(R.id.chPagada)
            val checkBox2 = findViewById<CheckBox>(R.id.chAnulada)
            val checkBox3 = findViewById<CheckBox>(R.id.chCuotaFija)
            val checkBox4 = findViewById<CheckBox>(R.id.chPendientes)
            val checkBox5 = findViewById<CheckBox>(R.id.chPlan)

            slider.progress = 0
            checkBox1.isChecked = false
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            checkBox4.isChecked = false
            checkBox5.isChecked = false

            Toast.makeText(this,"hola", Toast.LENGTH_SHORT).show()
        }

}}
