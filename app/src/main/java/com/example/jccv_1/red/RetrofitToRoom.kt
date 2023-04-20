package com.example.jccv_1.red

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jccv_1.database.facturaDAO
import com.example.jccv_1.database.facturasAPP
import com.example.jccv_1.modeladoDatos.FactForm
import com.example.jccv_1.modeladoDatos.Facturas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitToRoom(application: Application) {

     val apiService: ApiService = RetrofitAPI.getApiService()

     val dataDao: facturaDAO = facturasAPP.room.facturaDAO()


    suspend fun getMyData(): List<Facturas> {
       // val data = MutableLiveData<List<Facturas>>()
        apiService.getFacturas().enqueue(object : Callback<FactForm> {

            override fun onResponse(call: Call<FactForm>, response: Response<FactForm>) {
                if (response.isSuccessful) {
                    val myDataList = response.body()!!.facturas

                    CoroutineScope(Dispatchers.IO).launch {
                        dataDao.insert(myDataList)


                    }
                    //data.value = myDataList

                }
            }

            override fun onFailure(call: Call<FactForm>, t: Throwable) {
                // Manejar el error
            }

        })
        return dataDao.getALL()
        Log.d("prueba", dataDao.getALL().toString())
    }
}
