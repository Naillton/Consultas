package com.nailton.consultas.data.datasourceimplementation

import com.nailton.consultas.data.Consulta
import com.nailton.consultas.data.datasource.ConsultaCacheDataSource

class ConsultaCacheDataSourceImplementation: ConsultaCacheDataSource {
    private var consultaList = ArrayList<Consulta>()

    override suspend fun getConsultasFromCache(): List<Consulta> {
        return this.consultaList
    }

    override suspend fun saveConsultasToCache(consultas: List<Consulta>) {
        this.consultaList.clear()
        this.consultaList = ArrayList(consultas)
    }
}