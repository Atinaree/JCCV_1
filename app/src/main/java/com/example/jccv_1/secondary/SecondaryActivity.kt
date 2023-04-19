package com.example.jccv_1.secondary
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import com.example.jccv_1.R
import com.example.jccv_1.databinding.SecondaryActivityBinding
import com.example.jccv_1.main_model.CustomAdapter
import java.util.*

class SecondaryActivity : Activity() {
    lateinit var binding: SecondaryActivityBinding
    lateinit var itemfiltross: LinearLayout
    lateinit var botonaplicar: Button
    lateinit var adapter: CustomAdapter
    private var fechaInicio: Date? = null
    lateinit var hola: String

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
        val button1 = item.findViewById<Button>(R.id.botonFechaIni)
        val button2 = item.findViewById<Button>(R.id.botonFechaFin)
        DatePickerManager(button1)
        DatePickerManager(button2)

        // Crear un DatePickerManager para el botón 1
        val datePickerManager1 = DatePickerManager(button1)

        // Crear un DatePickerManager para el botón 2
        val datePickerManager2 = DatePickerManager(button2)


// Establecer la fecha mínima para el DatePicker del botón 2 como la fecha seleccionada en el botón 1
        button2.setOnClickListener {
            val minDate = datePickerManager1.getDate() // Obtener la fecha seleccionada en el botón 1
            if (minDate != null ) {
                datePickerManager2.setMinDate(minDate) // Establecer la fecha mínima para el DatePicker del botón 2
            }
        }


        //Boton para cerrar la vista
        binding.buttonFilter.setOnClickListener {
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
            val intent = Intent(this, SecondaryActivity::class.java)


        }

    }
}
