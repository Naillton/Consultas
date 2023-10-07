package com.nailton.consultas.presentation.configmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nailton.consultas.domain.usecases.CreateQueryUseCase
import com.nailton.consultas.domain.usecases.DeleteQueryUseCase
import com.nailton.consultas.domain.usecases.GetConsultaUseCase
import com.nailton.consultas.domain.usecases.LoginUseCase
import com.nailton.consultas.domain.usecases.OutApplicationUseCase
import com.nailton.consultas.domain.usecases.PersistUseCase
import com.nailton.consultas.domain.usecases.UpdateConsultasUseCase
import java.lang.IllegalArgumentException


class ViewModelFactory(
    private val getConsultaUseCase: GetConsultaUseCase,
    private val updateConsultasUseCase: UpdateConsultasUseCase,
    private val loginUseCase: LoginUseCase,
    private val persistUseCase: PersistUseCase,
    private val outApplicationUseCase: OutApplicationUseCase,
    private val createQueryUseCase: CreateQueryUseCase,
    private val deleteQueryUseCase: DeleteQueryUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(
                getConsultaUseCase,
                updateConsultasUseCase,
                loginUseCase,
                persistUseCase,
                outApplicationUseCase,
                createQueryUseCase,
                deleteQueryUseCase) as T
        }
        throw IllegalArgumentException("View Model not found")
    }
}