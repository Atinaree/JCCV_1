package com.example.jccv_1.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jccv_1.modeladoDatos.Facturas
@Dao
interface facturaDAO {
    @Insert
    fun insert(facturas: List<Facturas>)
    @Query("SELECT * FROM facturas WHERE id = :id")
    fun getById(id: Int): Facturas
    @Query("SELECT * FROM facturas")
    fun getALL(): List<Facturas>
    @Query("SELECT * FROM facturas WHERE fecha = :fecha")
    fun getByFecha(fecha: String): Facturas
    @Query("DELETE FROM facturas")
    suspend fun eliminar()
}
