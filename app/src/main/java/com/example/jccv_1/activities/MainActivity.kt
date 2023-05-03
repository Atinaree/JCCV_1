package com.example.jccv_1.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = CustomAdapter()
    val dataDao: facturaDAO = facturasAPP.room.facturaDAO()
    lateinit var fechaini: String
    lateinit var fechafin: String
    lateinit var pagadass: String
    lateinit var anuladass: String
    lateinit var cfijass: String
    lateinit var pendientess: String
    lateinit var plans: String
    var importeSlider: Double = 0.0
    var lista = emptyList<Facturas>()
    var importeSelec = ""
    private val viewModel: MainViewModel by viewModels {MainViewModel.viewModelFactory(emptyList())}
    private val secondaryLauncher =
        registerForActivityResult(StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                fechaini = activityResult.data?.getStringExtra(fecha).orEmpty()
                fechafin = activityResult.data?.getStringExtra(fecha2).orEmpty()
                importeSelec = activityResult.data?.getStringExtra(importe).orEmpty()
                pagadass = activityResult.data?.getStringExtra(pagadas).orEmpty()
                anuladass = activityResult.data?.getStringExtra(anuladas).orEmpty()
                cfijass = activityResult.data?.getStringExtra(cfija).orEmpty()
                pendientess = activityResult.data?.getStringExtra(pendientes).orEmpty()
                plans = activityResult.data?.getStringExtra(plan).orEmpty()
                aplicarFiltros()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofitToRoom = RetrofitToRoom(application)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            val myDataList = withContext(Dispatchers.IO) {
                retrofitToRoom.getMyData()
            }
            viewModel.getFacturas(myDataList)
            adapter.setData(myDataList as ArrayList<Facturas>)
            var ordenIMport = myDataList.sortedByDescending { facturas: Facturas -> facturas.importeOrdenacion  }
            importeSlider = ordenIMport.first().importeOrdenacion
        }
        viewModel.facturasLiveData.observe(this, Observer {
            adapter.facturasList = it as ArrayList<Facturas>
            adapter.notifyDataSetChanged()
        })
        binding.buttonFilter.setOnClickListener {
            val intent = Intent(this, SecondaryActivity::class.java)
            intent.putExtra("importeSl", importeSlider)
            secondaryLauncher.launch(intent)
        }
    }
    fun aplicarFiltros() {
        GlobalScope.launch {
            //Cada vez que se aplica limpiamos la lista y la seteamos con todos
            lista = dataDao.getALL()

            //Trozo filtro por check
            var filtroPagadas = lista.filter { facturas: Facturas -> facturas.descEstado == "Pagada" }
            var filtroAnuladas = lista.filter { facturas: Facturas -> facturas.descEstado == "Anuladas" }
            var filtroCfija = lista.filter { facturas: Facturas -> facturas.descEstado == "Cuota Fija" }
            var filtroPendientes = lista.filter { facturas: Facturas -> facturas.descEstado == "Pendiente de pago" }
            var filtroPlan = lista.filter { facturas: Facturas -> facturas.descEstado == "Plan de pago" }

            // Si alguno esta marcado, lista a vacio y carga lo que este marcado.
            if ( pagadass == "Y" || pendientess == "Y" || anuladass == "Y" || cfijass == "Y" || plans == "Y") {
                var listaf = emptyList<Facturas>()

                if (pagadass == "Y") {
                    listaf = filtroPagadas
                }
                if (pendientess == "Y") {
                    listaf = listaf + filtroPendientes
                }
                if (anuladass == "Y") {
                    listaf = listaf + filtroAnuladas
                }
                if (cfijass == "Y") {
                    listaf = listaf + filtroCfija
                }
                if (plans == "Y") {
                    listaf = listaf + filtroPlan
                }
                // Finalmente carga la lista y la ordena por fecha
                lista = listaf.sortedBy { facturas: Facturas -> facturas.fecha }
            }

            // Trozo filtro por fecha
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            if (fechaini != "día/mes/año" && fechafin != "día/mes/año") {
                var inicial = lista.filter { factura: Facturas -> sdf.parse(factura.fecha) >= sdf.parse(fechaini) }
                var final = lista.filter { factura: Facturas -> sdf.parse(factura.fecha) <= sdf.parse(fechafin) }
                lista = inicial.intersect(final).toList()
            }else if (fechaini == "día/mes/año" && fechafin == "día/mes/año") {
            }else if (fechaini != "día/mes/año") {
                lista = lista.filter { factura: Facturas -> sdf.parse(factura.fecha) >= sdf.parse(fechaini)}
            }else if (fechafin != "día/mes/año") {
                lista = lista.filter { factura: Facturas -> sdf.parse(factura.fecha) <= sdf.parse(fechafin)}
            }

            //Trozo filtro por importe
            if (importeSelec != ""){
                lista = lista.filter { facturas: Facturas -> facturas.importeOrdenacion <=  importeSelec.toInt()}
                Log.d("listafiltradaimporte", lista.toString())
            }
            viewModel.getFacturas(lista)
            lista = dataDao.getALL()
        }
    }
}








