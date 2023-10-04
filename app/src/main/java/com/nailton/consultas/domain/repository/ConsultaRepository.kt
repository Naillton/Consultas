package com.nailton.consultas.domain.repository

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nailton.consultas.data.Consulta

interface ConsultaRepository {
    suspend fun getConsultas(): List<Consulta>?
    suspend fun updateConsultas(): List<Consulta>?
    suspend fun login(email: String, password: String): Boolean?
    suspend fun persistLogin(): FirebaseUser?
    suspend fun outApplication(): FirebaseAuth?
    suspend fun createQuery(
        pacienteEmail: String,
        pacienteNome: String,
        titulo: String,
        descricao: String,
    ): Boolean?
}