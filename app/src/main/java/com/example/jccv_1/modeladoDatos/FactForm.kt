package com.example.jccv_1.modeladoDatos
// Clase de datos con la estructura del jSon
data class FactForm (
    val numFacturas: Int,
    val facturas: List<Facturas>
)