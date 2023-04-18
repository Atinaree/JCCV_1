package com.example.jccv_1.secondary
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.core.view.size
import com.example.jccv_1.R
import com.example.jccv_1.databinding.SecondaryActivityBinding
import com.example.jccv_1.main_model.CustomAdapter
import java.text.SimpleDateFormat
import java.util.*


class SecondaryActivity : Activity() {
    lateinit var binding: SecondaryActivityBinding
    lateinit var itemfiltross: LinearLayout
    lateinit var botonaplicar: Button
    lateinit var adapter: CustomAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        // Configuracion regional
        val locale = Locale("es")
        Locale.setDefault(locale)
        val config = baseContext.resources.configuration
        config.setLocale(locale)
        baseContext.createConfigurationContext(config)

        super.onCreate(savedInstanceState)
        binding = SecondaryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cargar layout en el LinearLayout
        val inflater = LayoutInflater.from(this)
        itemfiltross = binding.itemfiltross
        val item = inflater.inflate(R.layout.itemfiltross, null)
        itemfiltross.addView(item)
//Botón Fecha Inicio
        val button1 = item.findViewById<Button>(R.id.botonFechaIni)
        button1.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,R.style.datePicker,
                { _, year, month, dayOfMonth ->
// Aquí puedes hacer algo con la fecha seleccionada, por ejemplo:
                    button1.text = "$year-${month+1}-$dayOfMonth"
                },
                year, month, day
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis() //Evita seleccionar fecha posterior al día actual
            datePickerDialog.show()
        }

//Botón Fecha Fin
        val button2 = item.findViewById<Button>(R.id.botonFechaFin)
        button2.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,R.style.datePicker,
                { _, year, month, dayOfMonth ->
// Aquí puedes hacer algo con la fecha seleccionada, por ejemplo:
                    button2.text = "$year-${month+1}-$dayOfMonth"
                },
                year, month, day
            )
            val startDate = button1.text.toString() //Obtiene la fecha seleccionada en el primer Date Picker
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.parse(startDate)
            datePickerDialog.datePicker.minDate = date.time //Evita seleccionar fecha anterior a la seleccionada en el primer Date Picker
            datePickerDialog.show()
        }


//Boton para cerrar la vista
        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            finish()
    }
// Boton para quitar filtros
        binding.button3.setOnClickListener {

            button1.text = "dia/mes/año"
            button2.text = "dia/mes/año"

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

        }
        //Boton aplicar
        botonaplicar = binding.button
        adapter = CustomAdapter()
        botonaplicar.setOnClickListener {
            Toast.makeText(this, "2323", Toast.LENGTH_SHORT).show()
        }



}}
