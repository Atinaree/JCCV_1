package com.example.jccv_1.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.jccv_1.modeladoDatos.Facturas
class MainViewModel(val Facturas: List<Facturas>): ViewModel() {
    val facturasLiveData = MutableLiveData<List<Facturas>>()
    fun getFacturas(list: List<Facturas>) {
        facturasLiveData.postValue(list)
    }
@Suppress("UNCHECKED_CAST")
    class viewModelFactory(private val Facturas: List<Facturas>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(Facturas) as T
    }
}
}








