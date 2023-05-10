package com.example.jccv_1.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.jccv_1.modeladoDatos.Facturas

class MainViewModel(val Facturas: List<Facturas>) : ViewModel() {
    // LiveData para almacenar y notificar cambios en la lista de facturas
    val facturasLiveData = MutableLiveData<List<Facturas>>()

    // Función para obtener las facturas y actualizar el LiveData
    fun getFacturas(list: List<Facturas>) {
        facturasLiveData.postValue(list)
    }

    // Clase Factory para crear instancias del MainViewModel
    // Esta clase es necesaria para que ViewModelProvider pueda crear instancias de ViewModel
    @Suppress("UNCHECKED_CAST")
    class viewModelFactory(private val Facturas: List<Facturas>) : ViewModelProvider.Factory {
        // Método create que crea una instancia de ViewModel
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            // Se crea una instancia de MainViewModel y se le pasa la lista de facturas
            // Se realiza un cast a T (tipo genérico) y se retorna
            return MainViewModel(Facturas) as T
        }
    }
}








