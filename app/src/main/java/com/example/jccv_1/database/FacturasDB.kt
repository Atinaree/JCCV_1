package com.example.jccv_1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jccv_1.modeladoDatos.Facturas

// Definición de la base de datos utilizando la anotación @Database
@Database(
    entities = [Facturas::class], // Especifica las entidades de la base de datos (en este caso, solo Facturas)
    version = 1 // Especifica la versión de la base de datos
)
abstract class FacturasDB : RoomDatabase() {
    abstract fun facturaDAO(): facturaDAO // Declara el método abstracto para obtener el DAO (Data Access Object) de Facturas
}
