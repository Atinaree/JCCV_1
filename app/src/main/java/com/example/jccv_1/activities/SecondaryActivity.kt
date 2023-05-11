package com.example.jccv_1.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import com.example.jccv_1.R
import com.example.jccv_1.databinding.SecondaryActivityBinding
import com.example.jccv_1.modeladoDatos.CustomAdapter
import com.example.jccv_1.secondary.DatePickerDesde
import com.example.jccv_1.secondary.DatePickerHasta
import java.lang.Math.ceil
import java.text.SimpleDateFormat
import java.util.*

class SecondaryActivity : Activity() {
    lateinit var binding: SecondaryActivityBinding
    lateinit var itemfiltross: LinearLayout
    lateinit var botonaplicar: Button
    lateinit var adapter: CustomAdapter
    lateinit var fechaInicial: String
    lateinit var fechaFinal: String
    var importeSeleccionado = ""

    companion object {
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
        //Binding
        super.onCreate(savedInstanceState)
        binding = SecondaryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)




        // Cargar layout en el LinearLayout
        val inflater = LayoutInflater.from(this)
        itemfiltross = binding.itemfiltross
        //Inflamos la vista del item
        val item = inflater.inflate(R.layout.itemfiltross, null)
        itemfiltross.addView(item)


        val button1 = item.findViewById<Button>(R.id.botonFechaIni)
        val button2 = item.findViewById<Button>(R.id.botonFechaFin)
        val importe = item.findViewById<TextView>(R.id.importeMAX)
        findViewById<TextView>(R.id.importeMIN).text = getString(R.string.euro, "0")


//Montamos los datapickers en sus respectivos botones

        DatePickerDesde(button1)
        DatePickerHasta(button2)
        /*
         Evento listener para establecer la fecha minima del segundo boton, para el
         caso en que se seleccione ambas fechas ( desde y hasta) para que la fecha hasta
         no pueda ser anterior a la de desde.
         */
        button2.setOnClickListener() {
            if (button1.text.toString() != "día/mes/año") {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                DatePickerHasta(button2).setMinDate(sdf.parse(button1.text.toString()))
            } else {
                DatePickerHasta(button2)
            }
        }
        //Boton para cerrar la vista sin aplicar cambios
        binding.buttonFilter.setOnClickListener {
            finish()
        }

        // Boton para quitar filtros (resetea los valores)
        binding.button3.setOnClickListener {
            button1.text = getText(R.string.textoBonotesFecha)
            button2.text = getText(R.string.textoBonotesFecha)

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
            aplicarFiltros()
        }

        /*
        Localizamos el slider, le establecemos su valor maximo en un redondeo de
        importeSL a entero y concatenado con el simbolo de €. Este importe lo traemos de la MainActivity
        cuando hemos abierto la SecondaryActivity, y es el valor mas alto que encuentre de la lista de
        facturas.
         */
        val importeSl = intent.getDoubleExtra("importeSl", 0.0)
        importe.text = getString(R.string.euro,ceil(importeSl).toInt().toString())
        val barra = findViewById<SeekBar>(R.id.barraImporte)
        val maximo = ceil(importeSl).toInt()
        barra.max = maximo // Establecer el valor máximo a la seekbar
        //Listener de modificacion del slider
        barra.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Aquí se ejecutará el código cuando el valor de la seekbar cambie
                val importeActual = item.findViewById<TextView>(R.id.importeActual)
                importeActual.setText(getString(R.string.euro,progress.toString() ))
            }

            //Aqui va el codigo que se ejecuta mientras se esta modificando
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            /*
            Aqui va el codigo que se ejecuta cuando se para de modificar,
            en este caso establecemos el valor del importe seleccionado.
             */
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                importeSeleccionado = barra.progress.toString()
            }
        })
    }

    fun aplicarFiltros() {
        val fecha1 = findViewById<Button>(R.id.botonFechaIni)
        val fecha22 = findViewById<Button>(R.id.botonFechaFin)
        val checkPagadas = findViewById<CheckBox>(R.id.chPagada)
        val checkAnuladas = findViewById<CheckBox>(R.id.chAnulada)
        val checkCFija = findViewById<CheckBox>(R.id.chCuotaFija)
        val checkPendientes = findViewById<CheckBox>(R.id.chPendientes)
        val checkPlan = findViewById<CheckBox>(R.id.chPlan)
        val intent = Intent()

        fechaInicial = fecha1.text.toString()
        fechaFinal = fecha22.text.toString()
        //Se pasa la fecha inicial, final y el importe
        intent.putExtra(fecha, fechaInicial)
        intent.putExtra(fecha2, fechaFinal)
        intent.putExtra(importe, importeSeleccionado)
        /*
        A continuacion se evalua si esta el filtro marcado o no, y de ser asi, se guarda
        una Y en las distintas variables, a la espera de pasar dicho valor despues a traves
        del inten.
         */
        if (checkPagadas.isChecked) intent.putExtra(pagadas, "Y")
        if (checkAnuladas.isChecked) intent.putExtra(anuladas, "Y")
        if (checkCFija.isChecked) intent.putExtra(cfija, "Y")
        if (checkPendientes.isChecked) intent.putExtra(pendientes, "Y")
        if (checkPlan.isChecked) intent.putExtra(plan, "Y")
        /*
        Mandamos el resultado a la MainActivity y cerramos la actividad
         */
        setResult(RESULT_OK, intent)
        finish()

    }
}



