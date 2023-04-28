package com.example.jccv_1.modeladoDatos
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity (tableName = "facturas")
data class Facturas(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val descEstado: String,
    val importeOrdenacion: Double,
    val fecha: String)
