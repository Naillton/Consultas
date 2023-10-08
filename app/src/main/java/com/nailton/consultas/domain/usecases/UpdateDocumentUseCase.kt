package com.nailton.consultas.domain.usecases

import com.nailton.consultas.data.Consulta
import com.nailton.consultas.domain.repository.ConsultaRepository

class UpdateDocumentUseCase(private val consultaRepository: ConsultaRepository) {
    suspend fun updateDocument(
        userId: String,
        pacienteEmail: String,
        pacienteNome: String,
        titulo: String,
        descricao: String
    ): Boolean = consultaRepository.updateQuery(
        userId,
        pacienteEmail,
        pacienteNome,
        titulo,
        descricao
    )
}