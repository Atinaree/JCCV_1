package com.example.jccv_1.database

import android.app.Application
import androidx.room.Room

class facturasAPP : Application() {
    companion object {
        lateinit var room: FacturasDB
    }

    override fun onCreate() {
        super.onCreate()

        // Crea una instancia de la base de datos usando ROOM
        room = Room.databaseBuilder(applicationContext, FacturasDB::class.java, "facturas")
            .fallbackToDestructiveMigration()
            .build()
    }
}