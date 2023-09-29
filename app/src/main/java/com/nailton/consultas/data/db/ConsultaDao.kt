package com.nailton.consultas.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nailton.consultas.data.Consulta

@Dao
interface ConsultaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(consultas: List<Consulta>)

    @Query("DELETE FROM consultas")
    suspend fun deleteAllConsultas()

    @Query("SELECT * FROM consultas")
    suspend fun getAllConsultas(): List<Consulta>
}