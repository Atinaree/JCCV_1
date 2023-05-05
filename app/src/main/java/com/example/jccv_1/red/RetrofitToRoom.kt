package com.example.jccv_1.red

import android.app.Application
import android.util.Log
import com.example.jccv_1.database.facturaDAO
import com.example.jccv_1.database.facturasAPP
import com.example.jccv_1.modeladoDatos.Facturas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RetrofitToRoom(application: Application) {
    private val apiService: ApiService = RetrofitAPI.getApiService()
    private val mockService: MockService = RetrofitAPI.getMockService()
    private val dataDao: facturaDAO = facturasAPP.room.facturaDAO()
    suspend fun getMyData(): List<Facturas> {
        return withContext(Dispatchers.IO) {
            try {
                val response = try {
                    Log.d("holi", "entra por retrofit")

                    apiService.getFacturas().execute()

                } catch (e: Exception) {
                    Log.d("holi", "entra por mocko")

                    mockService.geFacturasMock()?.execute()
                }
                if (response != null) {
                    if (response.isSuccessful) {
                        val myDataList = response?.body()!!.facturas
                        CoroutineScope(Dispatchers.IO).launch {
                            dataDao.eliminar()
                            dataDao.insert(myDataList)
                        }
                    }
                }
                dataDao.getALL()
            } catch (e: Exception) {
                // Manejar la excepción
                emptyList()
            }
        }
    }

}
