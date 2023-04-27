package com.example.jccv_1.activities
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import com.example.jccv_1.R
import com.example.jccv_1.database.facturaDAO
import com.example.jccv_1.database.facturasAPP
import com.example.jccv_1.databinding.SecondaryActivityBinding
import com.example.jccv_1.modeladoDatos.CustomAdapter
import com.example.jccv_1.secondary.DatePickerManager
import kotlinx.coroutines.*
import java.lang.Math.ceil
import java.util.*
class SecondaryActivity : Activity() {
    lateinit var binding: SecondaryActivityBinding
    lateinit var itemfiltross: LinearLayout
    lateinit var botonaplicar: Button
    lateinit var adapter: CustomAdapter
    lateinit var fechaInicial: String
    lateinit var fechaFinal: String
    var importeSeleccionado = ""
    val dataDao: facturaDAO = facturasAPP.room.facturaDAO()
    companion object{
        const val fecha = "fechaini"
        const val fecha2 = "fechafin"
        const val importe = "importe"
        const val pagadas = "pagadas"
        const val anuladas = "anuladas"
        const val cfija = "cfija"
        const val pendientes = "pendientes"
        const val plan = "plan"
    }
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
        val importe = item.findViewById<TextView>(R.id.importeMAX)

        val importeSl = intent.getDoubleExtra("importeSl", 0.0)
        importe.text = ceil(importeSl).toInt().toString() + "€"

        Log.d("aaaa", importe.text.toString())

        DatePickerManager(button1)
        DatePickerManager(button2)
        // Crear un DatePickerManager para el botón 1
        val datePickerManager1 = DatePickerManager(button1)
        // Crear un DatePickerManager para el botón 2
        val datePickerManager2 = DatePickerManager(button2)
// Establecer la fecha mínima para el DatePicker del botón 2 como la fecha seleccionada en el botón 1
        button2.setOnClickListener {
            val minDate =
                datePickerManager1.getDate() // Obtener la fecha seleccionada en el botón 1
            if (minDate != null) {
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
            GlobalScope.launch {
                aplicarFiltros()
            }
        }

        val barra = findViewById<SeekBar>(R.id.barraImporte)
        val maximo = ceil(importeSl).toInt()// Valor máximo deseado para la seekbar
        barra.max = maximo // Establecer el valor máximo a la seekbar
        barra.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Aquí se ejecutará el código cuando el valor de la seekbar cambie
                // Puedes usar la variable progress para obtener el valor actual de la seekbar

                val importeActual = item.findViewById<TextView>(R.id.importeActual)
                importeActual.setText(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Aquí se ejecutará el código cuando el usuario toque la seekbar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                importeSeleccionado = barra.progress.toString()

            }
        })



    }



     suspend fun aplicarFiltros() {
        withContext(Dispatchers.IO) {
            val fecha1 = findViewById<Button>(R.id.botonFechaIni)
            val fecha22 = findViewById<Button>(R.id.botonFechaFin)
            val slider = findViewById<SeekBar>(R.id.barraImporte)
            val checkBox1 = findViewById<CheckBox>(R.id.chPagada)
            val checkBox2 = findViewById<CheckBox>(R.id.chAnulada)
            val checkBox3 = findViewById<CheckBox>(R.id.chCuotaFija)
            val checkBox4 = findViewById<CheckBox>(R.id.chPendientes)
            val checkBox5 = findViewById<CheckBox>(R.id.chPlan)
            val intent = Intent()
            fechaInicial = fecha1.text.toString()
            fechaFinal = fecha22.text.toString()

            intent.putExtra(fecha, fechaInicial)
            intent.putExtra(fecha2, fechaFinal)
            intent.putExtra(importe, importeSeleccionado)
            if (checkBox1.isChecked)
                intent.putExtra(pagadas, "y")
            if (checkBox2.isChecked)
                intent.putExtra(anuladas, "y")
            if (checkBox3.isChecked)
                intent.putExtra(cfija, "y")
            if (checkBox4.isChecked)
                intent.putExtra(pendientes, "y")
            if (checkBox5.isChecked)
                intent.putExtra(plan, "y")

            setResult(RESULT_OK, intent)

            finish()
        }
    }

}



