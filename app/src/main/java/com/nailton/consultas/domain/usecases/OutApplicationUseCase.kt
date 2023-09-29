package com.nailton.consultas.domain.usecases

import com.google.firebase.auth.FirebaseAuth
import com.nailton.consultas.domain.repository.ConsultaRepository

class OutApplicationUseCase(private val consultaRepository: ConsultaRepository) {
    suspend fun outApplication(): FirebaseAuth? = consultaRepository.outApplication()
}