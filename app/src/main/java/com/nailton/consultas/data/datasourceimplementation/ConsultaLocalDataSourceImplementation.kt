package com.nailton.consultas.data.datasourceimplementation

import android.util.Log
import com.nailton.consultas.data.Consulta
import com.nailton.consultas.data.datasource.ConsultaLocalDataSource
import com.nailton.consultas.data.db.ConsultaDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ConsultaLocalDataSourceImplementation(
    private val consultaDao: ConsultaDao
): ConsultaLocalDataSource {

    override suspend fun getConsultasFromDB(): List<Consulta> {
        val func = CoroutineScope(Dispatchers.Default).async {
            consultaDao.getAllConsultas()
        }
        return func.await()
    }

    override suspend fun saveConsultasDB(consultas: List<Consulta>) {
        CoroutineScope(Dispatchers.IO).launch {
            consultaDao.save(consultas)
        }
    }

    override suspend fun deleteConsultasDB() {
        CoroutineScope(Dispatchers.IO).launch {
            consultaDao.deleteAllConsultas()
        }
    }
}