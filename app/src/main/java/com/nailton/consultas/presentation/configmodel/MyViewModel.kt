package com.nailton.consultas.presentation.configmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nailton.consultas.data.Consulta
import com.nailton.consultas.domain.usecases.CreateQueryUseCase
import com.nailton.consultas.domain.usecases.DeleteQueryUseCase
import com.nailton.consultas.domain.usecases.GetConsultaUseCase
import com.nailton.consultas.domain.usecases.LoginUseCase
import com.nailton.consultas.domain.usecases.OutApplicationUseCase
import com.nailton.consultas.domain.usecases.PersistUseCase
import com.nailton.consultas.domain.usecases.UpdateConsultasUseCase
import com.nailton.consultas.domain.usecases.UpdateDocumentUseCase

class MyViewModel(
    private val getConsultaUseCase: GetConsultaUseCase,
    private val updateConsultasUseCase: UpdateConsultasUseCase,
    private val loginUseCase: LoginUseCase,
    private val persistUseCase: PersistUseCase,
    private val outApplicationUseCase: OutApplicationUseCase,
    private val createQueryUseCase: CreateQueryUseCase,
    private val deleteQueryUseCase: DeleteQueryUseCase,
    private val updateDocumentUseCase: UpdateDocumentUseCase
): ViewModel() {
    fun getConsultas() = liveData {
        val consultaList = getConsultaUseCase.getConsultas()
        emit(consultaList)
    }

    fun updateConsultas() = liveData {
        val consultaList = updateConsultasUseCase.updateConsulta()
        emit(consultaList)
    }

    fun login(email: String, password: String) = liveData {
        val loginUser = loginUseCase.loginUser(email, password)
        emit(loginUser)
    }

    fun persistLogin() = liveData {
        val persist = persistUseCase.persistUser()
        emit(persist)
    }

    fun outApplication() = liveData {
        val out = outApplicationUseCase.outApplication()
        emit(out)
    }

    fun createQuery(
                    pacienteEmail: String,
                    pacienteNome: String,
                    titulo: String,
                    descricao: String) = liveData {
        val createQuery = createQueryUseCase.createQuery(
            pacienteEmail,
            pacienteNome,
            titulo,
            descricao)
        emit(createQuery)
    }

    fun deleteQuery(consulta: Consulta) = liveData {
        val delete = deleteQueryUseCase.deleteQuery(consulta)
        emit(delete)
    }

    fun updateQuery(userId: String,
                    pacienteEmail: String,
                    pacienteNome: String,
                    titulo: String,
                    descricao: String) = liveData {
        val update = updateDocumentUseCase.updateDocument(
            userId,
            pacienteEmail,
            pacienteNome,
            titulo,
            descricao)
        emit(update)
    }
}