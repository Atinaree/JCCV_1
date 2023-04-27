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


    private val viewModel: MainViewModel by viewModels {MainViewModel.viewModelFactory(emptyList())}


    private val secondaryLauncher =
        registerForActivityResult(StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                fechaini = activityResult.data?.getStringExtra(fecha).orEmpty()
                fechafin = activityResult.data?.getStringExtra(fecha2).orEmpty()
                var importe = activityResult.data?.getStringExtra(importe).orEmpty()
                Log.d("hola",importe)
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
            var fecha1 = fechaini
            var fecha2 = fechafin

            var filtroPagadas = dataDao.getALL()
                .filter { facturas: Facturas -> facturas.descEstado == "Pagada" }
            var filtroAnuladas = dataDao.getALL()
                .filter { facturas: Facturas -> facturas.descEstado == "Anuladas" }
            var filtroCfija = dataDao.getALL()
                .filter { facturas: Facturas -> facturas.descEstado == "Cuota Fija" }
            var filtroPendientes = dataDao.getALL()
                .filter { facturas: Facturas -> facturas.descEstado == "Pendiente de pago" }
            var filtroPlan = dataDao.getALL()
                .filter { facturas: Facturas -> facturas.descEstado == "Plan de pago" }

            // Trozo filtro por fecha
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            var inicial = emptyList<Facturas>()
            var final = emptyList<Facturas>()
            Log.d("ffffff",fechaini.toString())
            if (fechaini =="día/mes/año"){
                }
                else
                {
                    inicial = dataDao.getALL().filter { factura: Facturas -> sdf.parse(factura.fecha) >= sdf.parse(fecha1)}
                }
            if (fechafin =="día/mes/año"){
                }
                else {
                final = dataDao.getALL().filter { factura: Facturas -> sdf.parse(factura.fecha) <= sdf.parse(fecha2)}
                }
            var filtrofecha = inicial.intersect(final).toList()

            var lista = filtrofecha










            viewModel.getFacturas(lista)

        }
    }
}








