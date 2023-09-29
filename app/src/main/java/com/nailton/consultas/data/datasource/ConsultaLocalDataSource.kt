package com.nailton.consultas.data.datasource

import com.nailton.consultas.data.Consulta

interface ConsultaLocalDataSource {
    suspend fun getConsultasFromDB(): List<Consulta>
    suspend fun saveConsultasDB(consultas: List<Consulta>)
    suspend fun deleteConsultasDB()
}