package com.nailton.consultas.data.datasource

import com.nailton.consultas.data.Consulta

interface ConsultaCacheDataSource {
    suspend fun getConsultasFromCache(): List<Consulta>
    suspend fun saveConsultasToCache(consultas: List<Consulta>)
}