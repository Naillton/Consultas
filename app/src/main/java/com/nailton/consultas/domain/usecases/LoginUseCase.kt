package com.nailton.consultas.domain.usecases

import com.nailton.consultas.domain.repository.ConsultaRepository

class LoginUseCase(
    private val consultaRepository: ConsultaRepository) {
    suspend fun loginUser(
        email: String,
        password: String): Boolean? = consultaRepository.login(email, password)
}