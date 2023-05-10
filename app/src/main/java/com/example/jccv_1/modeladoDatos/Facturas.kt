package com.example.jccv_1.modeladoDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

// Anotación @Entity indica que esta clase representa una entidad de la base de datos
@Entity(tableName = "facturas")
data class Facturas(
    // Anotación @PrimaryKey indica que el campo "id" es la clave primaria de la entidad
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    // Campo que almacena la descripción del estado de la factura
    val descEstado: String,

    // Campo que almacena el importe de la factura
    val importeOrdenacion: Double,

    // Campo que almacena la fecha de la factura
    val fecha: String
)
