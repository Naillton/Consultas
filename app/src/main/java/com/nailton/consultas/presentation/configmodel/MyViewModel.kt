package com.nailton.consultas.presentation.configmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nailton.consultas.domain.usecases.GetConsultaUseCase
import com.nailton.consultas.domain.usecases.LoginUseCase
import com.nailton.consultas.domain.usecases.OutApplicationUseCase
import com.nailton.consultas.domain.usecases.PersistUseCase
import com.nailton.consultas.domain.usecases.UpdateConsultasUseCase

class MyViewModel(
    private val getConsultaUseCase: GetConsultaUseCase,
    private val updateConsultasUseCase: UpdateConsultasUseCase,
    private val loginUseCase: LoginUseCase,
    private val persistUseCase: PersistUseCase,
    private val outApplicationUseCase: OutApplicationUseCase
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
}