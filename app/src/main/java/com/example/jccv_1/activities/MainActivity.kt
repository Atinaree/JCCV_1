package com.example.jccv_1.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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
import com.example.jccv_1.database.facturaDAO
import com.example.jccv_1.database.facturasAPP
import com.example.jccv_1.red.RetrofitToRoom
import com.example.jccv_1.databinding.ActivityMainBinding
import com.example.jccv_1.modeladoDatos.CustomAdapter
import com.example.jccv_1.modeladoDatos.Facturas
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = CustomAdapter()

    //variable que contiene la lista completa guardada en la base de datos
    val dataDao: facturaDAO = facturasAPP.room.facturaDAO()

    //variables para guardar el valor de los filtros
    lateinit var fechaini: String
    lateinit var fechafin: String
    lateinit var pagadass: String
    lateinit var anuladass: String
    lateinit var cfijass: String
    lateinit var pendientess: String
    lateinit var plans: String

    /*
    Variables para guardar el importe seleccionado y para definir el valor maximo del slider
    del SecondaryActivity
     */
    var importeSlider: Double = 0.0
    var importeSelec = ""

    // Lista vacia que usarmeos despues para ir recargando datos de facturas
    var lista = emptyList<Facturas>()
    private val viewModel: MainViewModel by viewModels { MainViewModel.viewModelFactory(emptyList()) }

    /*
    configuramos el launcher de SecondaryActivity para que espere un resultado y retraiga
    los valores de las distintas variables, que se corresponden con los distintos filtros.
     */
    private val secondaryLauncher =
        registerForActivityResult(StartActivityForResult()) { activityResult ->
            /*
            Configuramos el launcher para  que espere un resultado para las siguientes variables, luego en esta
            misma actividad en la funcion aplicarFiltros() evaluaremos el valor de dichas variables para extrapolar
            el comportamiento de los distintos filtros. De esta forma evitamos tener que serializar/deserializar la
            respuesta de la base de datos cada vez que se apliquen filtros.
             */
            if (activityResult.resultCode == RESULT_OK) {
                fechaini = activityResult.data?.getStringExtra(fecha).orEmpty()
                fechafin = activityResult.data?.getStringExtra(fecha2).orEmpty()
                importeSelec = activityResult.data?.getStringExtra(importe).orEmpty()
                pagadass = activityResult.data?.getStringExtra(pagadas).orEmpty()
                anuladass = activityResult.data?.getStringExtra(anuladas).orEmpty()
                cfijass = activityResult.data?.getStringExtra(cfija).orEmpty()
                pendientess = activityResult.data?.getStringExtra(pendientes).orEmpty()
                plans = activityResult.data?.getStringExtra(plan).orEmpty()
                /*
                Aplicamos los filtros
                 */
                aplicarFiltros()
                /*
                Reseteamos el valor de importe maximo del slider cada vez que vuelve del secondary
                 */
                val retrofitToRoom = RetrofitToRoom(application)
                GlobalScope.launch {
                    lista = retrofitToRoom.getMyData()
                    var sortedDataList =
                        lista.sortedByDescending { facturas -> facturas.importeOrdenacion }
                    importeSlider = sortedDataList.first().importeOrdenacion
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Establecemos el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Instancia de la clase con parametro application
        val retrofitToRoom = RetrofitToRoom(application)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            // Obtener los datos de forma asíncrona en un hilo de entrada/salida
            val myDataList = withContext(Dispatchers.IO) {
                retrofitToRoom.getMyData()
            }
            // Ordenar la lista por importe de mayor a menor
            var sortedDataList =
                myDataList.sortedByDescending { facturas -> facturas.importeOrdenacion }
            /// Obtener el importe de la primera factura en la lista ordenada
            importeSlider = sortedDataList.firstOrNull()?.importeOrdenacion ?: 0.0
            // Actualizar los datos del ViewModel y del adaptador
            viewModel.getFacturas(myDataList)
            adapter.setData(myDataList as ArrayList<Facturas>)
        }
        //Preparamos el observador para saber cuando cambia la lista de facturas.
        viewModel.facturasLiveData.observe(this, Observer {
            adapter.facturasList = it as ArrayList<Facturas>
            adapter.notifyDataSetChanged()
        })
        /*
        Evento listener del boton de filtros que abre la SecondaryActivity y le pasa la info
        del importe maximo de la barra Slider. Usamos el launcher configurado anteriormente.
         */
        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra("importeSl", importeSlider)
            secondaryLauncher.launch(intent)
        }
        /*
        Evento listener del boton que pone "consumo" que nos devuelve a la pantalla principal
         */
        binding.button2.setOnClickListener() {
            finish()
        }
    }

    fun aplicarFiltros() {
        GlobalScope.launch {
            //Cada vez que se aplica limpiamos la lista y la seteamos con todos
            lista = dataDao.getALL()
            // Trozo filtro por fecha
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            /*
             Si hay 2 fechas montamos dos variables que tengan las distintas facturas y sacamos la
             interseccion (las que coinciden)
             */
            if (fechaini != "día/mes/año" && fechafin != "día/mes/año") {
                var inicial = lista.filter { factura: Facturas ->
                    sdf.parse(factura.fecha) >= sdf.parse(fechaini)
                }
                var final = lista.filter { factura: Facturas ->
                    sdf.parse(factura.fecha) <= sdf.parse(fechafin)
                }
                /*
                 Hay que decirle que no sea empty, porque si no crashea al intentar hacer la interseccion. Este
                 empty se refiere a que no encuentre facturas, no a que no haya fecha seleccionada en inicial.
                 */
                if (inicial.isNotEmpty()) {
                    lista = inicial.intersect(final).toList()
                } else {
                    lista = lista.filter { factura: Facturas ->
                        sdf.parse(factura.fecha) <= sdf.parse(fechafin) && sdf.parse(factura.fecha) >= sdf.parse(
                            fechaini
                        )
                    }
                }
                //Si no hay fecha ( no filtra por aqui por lo que no hace nada)
            } else if (fechaini == "día/mes/año" && fechafin == "día/mes/año") {
                //Si solo hay una fecha(inicial)
            } else if (fechaini != "día/mes/año") {
                lista = lista.filter { factura: Facturas ->
                    sdf.parse(factura.fecha) >= sdf.parse(fechaini)
                }
                //Si solo hay una fecha(final)
            } else if (fechafin != "día/mes/año") {
                lista = lista.filter { factura: Facturas ->
                    sdf.parse(factura.fecha) <= sdf.parse(fechafin)
                }
            }
            //Trozo filtro por check. Filtramos la lista anterior.
            var filtroPagadas =
                lista.filter { facturas: Facturas -> facturas.descEstado == "Pagada" }
            var filtroAnuladas =
                lista.filter { facturas: Facturas -> facturas.descEstado == "Anuladas" }
            var filtroCfija =
                lista.filter { facturas: Facturas -> facturas.descEstado == "Cuota Fija" }
            var filtroPendientes =
                lista.filter { facturas: Facturas -> facturas.descEstado == "Pendiente de pago" }
            var filtroPlan =
                lista.filter { facturas: Facturas -> facturas.descEstado == "Plan de pago" }
            // Si alguno esta marcado, lista vacia y carga lo que este marcado.
            if (pagadass == "Y" || pendientess == "Y" || anuladass == "Y" || cfijass == "Y" || plans == "Y") {
                var listaf = emptyList<Facturas>()
                if (pagadass == "Y") {
                    listaf = filtroPagadas
                }
                if (pendientess == "Y") {
                    listaf = (listaf + filtroPendientes)
                }
                if (anuladass == "Y") {
                    listaf = (listaf + filtroAnuladas)
                }
                if (cfijass == "Y") {
                    listaf = (listaf + filtroCfija)
                }
                if (plans == "Y") {
                    listaf = (listaf + filtroPlan)
                }
                // Finalmente iguala la lista de filtros check a la lista
                lista = listaf
            }
            /*
            Trozo filtro por importe. Primero controlamos que no sea vacio (que no se haya puesto ningun
            valor, y despues que no sea 0. Esto lo hago porque descubri que habia un bug que si se movia el
            slider del valor inicial y se volvia a poner en 0 explotaba la aplicacion.
             */
            if (importeSelec != "") {
                if (importeSelec != "0") {
                    lista =
                        lista.filter { facturas: Facturas -> facturas.importeOrdenacion <= importeSelec.toInt() }
                }
            }
            viewModel.getFacturas(lista)
            lista = dataDao.getALL()
        }
    }
}








