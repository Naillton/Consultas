package com.nailton.consultas.domain.usecases

import com.nailton.consultas.data.Consulta
import com.nailton.consultas.domain.repository.ConsultaRepository

class DeleteQueryUseCase(private val consultaRepository: ConsultaRepository) {
    suspend fun deleteQuery(consulta: Consulta): Boolean = consultaRepository.deleteQuery(consulta)
}