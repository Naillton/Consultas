package com.nailton.consultas.domain.usecases

import com.nailton.consultas.domain.repository.ConsultaRepository

class CreateQueryUseCase(private val consultaRepository: ConsultaRepository) {
    suspend fun createQuery(
        pacienteEmail: String,
        pacienteNome: String,
        titulo: String,
        descricao: String
    ): Boolean? = consultaRepository.createQuery(
        pacienteEmail,
        pacienteNome,
        titulo,
        descricao)
}