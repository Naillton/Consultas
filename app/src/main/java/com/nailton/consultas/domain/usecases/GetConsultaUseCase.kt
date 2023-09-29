package com.nailton.consultas.domain.usecases

import com.nailton.consultas.data.Consulta
import com.nailton.consultas.domain.repository.ConsultaRepository

class GetConsultaUseCase(private val consultaRepository: ConsultaRepository) {
    suspend fun getConsultas(): List<Consulta>? = consultaRepository.getConsultas()
}