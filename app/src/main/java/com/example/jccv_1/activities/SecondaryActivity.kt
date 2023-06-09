package com.example.jccv_1.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    var importeSeleccionado = filtros.importefiltro

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
        button1.text = filtros.fecha1filtro
        val button2 = item.findViewById<Button>(R.id.botonFechaFin)
        button2.text = filtros.fecha2filtro
        val importeso = item.findViewById<SeekBar>(R.id.barraImporte)
        importeso.progress = filtros.importefiltro.toInt()
        var importeActual = item.findViewById<TextView>(R.id.importeActual)
        importeActual.text = importeso.progress.toString() + "€"
        val importe = item.findViewById<TextView>(R.id.importeMAX)

        var pagadacheck = item.findViewById<CheckBox>(R.id.chPagada)
        pagadacheck.isChecked = filtros.pagada.toBoolean()
        var anuladacheck = item.findViewById<CheckBox>(R.id.chAnulada)
        anuladacheck.isChecked = filtros.anulada.toBoolean()

        var cfijacheck = item.findViewById<CheckBox>(R.id.chCuotaFija)
        cfijacheck.isChecked = filtros.cfija.toBoolean()

        var pendientecheck = item.findViewById<CheckBox>(R.id.chPendientes)
        pendientecheck.isChecked = filtros.pendiente.toBoolean()

        var plancheck = item.findViewById<CheckBox>(R.id.chPlan)
        plancheck.isChecked = filtros.plan.toBoolean()


        //setear valor 0€ en la pantalla de filtros
        findViewById<TextView>(R.id.importeMIN).text = getString(R.string.euro, "0")


//Montamos los datapickers en sus respectivos botones

        button1.setOnClickListener(){
            //Si no hay fecha hasta abre el dialogo de serie
            if (button2.text.toString() == getString(R.string.diamesyaño)){
                DatePickerDesde(button1).showDatePickerDialog()
            } else {
            //Si tambien hay una fecha hasta
                DatePickerDesde(button1).showDatePickerDialog()
            }

        }
        button2.setOnClickListener() {
            if (button1.text.toString() == getString(R.string.diamesyaño)) {
                DatePickerHasta(button2).showDatePickerDialog()
                Log.d("1234", "primero")
            } else {
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                DatePickerHasta(button2).setMinDate(sdf.parse(button1.text.toString()))
                Log.d("1234", "segundo")

            }
        }
        //Boton para cerrar la vista sin aplicar cambios
        binding.buttonFilter.setOnClickListener {2
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
        importe.text = getString(R.string.euro, ceil(importeSl).toInt().toString())
        val barra = findViewById<SeekBar>(R.id.barraImporte)
        val maximo = ceil(importeSl).toInt()
        barra.max = maximo // Establecer el valor máximo a la seekbar
        //Listener de modificacion del slider
        barra.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Aquí se ejecutará el código cuando el valor de la seekbar cambie
                var importeActual = item.findViewById<TextView>(R.id.importeActual)
                importeActual.setText(getString(R.string.euro, progress.toString()))
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
        filtros.fecha1filtro = fecha1.text.toString()
        val fecha22 = findViewById<Button>(R.id.botonFechaFin)
        filtros.fecha2filtro = fecha22.text.toString()
        val slider = findViewById<SeekBar>(R.id.barraImporte)
        filtros.importefiltro = slider.progress.toString()
        val checkPagadas = findViewById<CheckBox>(R.id.chPagada)
        filtros.pagada = checkPagadas.isChecked.toString()
        val checkAnuladas = findViewById<CheckBox>(R.id.chAnulada)
        filtros.anulada = checkAnuladas.isChecked.toString()
        val checkCFija = findViewById<CheckBox>(R.id.chCuotaFija)
        filtros.cfija = checkCFija.isChecked.toString()
        val checkPendientes = findViewById<CheckBox>(R.id.chPendientes)
        filtros.pendiente = checkPendientes.isChecked.toString()
        val checkPlan = findViewById<CheckBox>(R.id.chPlan)
        filtros.plan = checkPlan.isChecked.toString()
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



