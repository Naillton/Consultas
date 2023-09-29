package com.nailton.consultas.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consultas")
data class Consulta(
    @PrimaryKey
    val userId: String,
    val email: String,
    val pacienteEmail: String,
    val pacienteNome: String,
    val titulo: String,
    val descricao: String,
    val isMedico: Boolean,
)
