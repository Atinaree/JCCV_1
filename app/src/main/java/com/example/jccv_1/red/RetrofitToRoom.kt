package com.example.jccv_1.red

import android.app.Application
import com.example.jccv_1.database.facturaDAO
import com.example.jccv_1.database.facturasAPP
import com.example.jccv_1.modeladoDatos.Facturas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RetrofitToRoom(application: Application) {
    companion object{
        var variabledepaso = 0

    }

    private val apiService: ApiService = RetrofitAPI.getApiService()
    private val mockService: MockService = RetrofitAPI.getMockService()
    private val dataDao: facturaDAO = facturasAPP.room.facturaDAO()

    suspend fun getMyData(): List<Facturas> {
        return withContext(Dispatchers.IO) {
            try {

                val response =
                    if(variabledepaso == 0){
                    // Llamada a la API utilizando Retrofit
                    apiService.getFacturas().execute()
                } else{
                    // En caso de error, utilizar el servicio de mock
                    mockService.geFacturasMock()?.execute()
                }

                if (response != null) {
                    if (response.isSuccessful) {
                        val myDataList = response?.body()!!.facturas

                        // Eliminar datos existentes en la base de datos
                        CoroutineScope(Dispatchers.IO).launch {
                            dataDao.eliminar()

                            // Insertar los nuevos datos en la base de datos
                            dataDao.insert(myDataList)
                        }
                    }
                }

                // Obtener todos los datos de la base de datos
                dataDao.getALL()
            } catch (e: Exception) {
                // Manejar la excepción y devolver una lista vacía
                emptyList()
            }
        }
    }
}
