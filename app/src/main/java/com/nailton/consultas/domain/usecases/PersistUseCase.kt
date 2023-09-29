package com.nailton.consultas.domain.usecases

import com.google.firebase.auth.FirebaseUser
import com.nailton.consultas.domain.repository.ConsultaRepository

class PersistUseCase(private val consultaRepository: ConsultaRepository) {
    suspend fun persistUser(): FirebaseUser? = consultaRepository.persistLogin()
}