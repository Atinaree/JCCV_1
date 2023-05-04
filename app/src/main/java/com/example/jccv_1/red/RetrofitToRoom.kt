package com.example.jccv_1.red

import android.app.Application

import com.example.jccv_1.database.facturaDAO
import com.example.jccv_1.database.facturasAPP
import com.example.jccv_1.modeladoDatos.FactForm
import com.example.jccv_1.modeladoDatos.Facturas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitToRoom(application: Application) {
    private val apiService: ApiService = RetrofitAPI.getApiService()
    private val dataDao: facturaDAO = facturasAPP.room.facturaDAO()

    suspend fun getMyData(): List<Facturas> {
        return withContext(Dispatchers.IO) {
            apiService.getFacturas().enqueue(object : Callback<FactForm> {
                override fun onResponse(call: Call<FactForm>, response: Response<FactForm>) {
                    if (response.isSuccessful) {
                        val myDataList = response.body()!!.facturas
                        CoroutineScope(Dispatchers.IO).launch {
                            dataDao.eliminar()
                            dataDao.insert(myDataList)
                        }
                    }
                }
                override fun onFailure(call: Call<FactForm>, t: Throwable) {
                }
            })
            dataDao.getALL()
        }
    }


    private suspend fun fetchDataFromApi(): List<Facturas> = withContext(Dispatchers.IO) {
        val response = apiService.getFacturas().execute()
        if (response.isSuccessful) {
            response.body()?.facturas ?: emptyList()
        } else {
            emptyList()
        }
    }

    private suspend fun insertDataIntoDatabase(dataList: List<Facturas>) {
        withContext(Dispatchers.IO) {
            dataDao.eliminar()
            dataDao.insert(dataList)
        }
    }
}
