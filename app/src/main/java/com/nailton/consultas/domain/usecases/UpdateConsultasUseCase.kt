package com.nailton.consultas.domain.usecases

import com.nailton.consultas.data.Consulta
import com.nailton.consultas.domain.repository.ConsultaRepository

class UpdateConsultasUseCase(private val consultaRepository: ConsultaRepository) {
    suspend fun updateConsulta(): List<Consulta>? = consultaRepository.updateConsultas()
}