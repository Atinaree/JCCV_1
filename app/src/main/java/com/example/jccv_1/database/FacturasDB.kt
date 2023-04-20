package com.example.jccv_1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jccv_1.modeladoDatos.Facturas

@Database(
    entities = [Facturas::class],
    version = 1
)

abstract class FacturasDB : RoomDatabase() {

    abstract fun facturaDAO(): facturaDAO



}