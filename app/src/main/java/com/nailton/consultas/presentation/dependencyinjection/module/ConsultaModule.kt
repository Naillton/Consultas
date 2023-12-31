package com.nailton.consultas.presentation.dependencyinjection.module

import com.nailton.consultas.domain.usecases.CreateQueryUseCase
import com.nailton.consultas.domain.usecases.DeleteQueryUseCase
import com.nailton.consultas.domain.usecases.GetConsultaUseCase
import com.nailton.consultas.domain.usecases.LoginUseCase
import com.nailton.consultas.domain.usecases.OutApplicationUseCase
import com.nailton.consultas.domain.usecases.PersistUseCase
import com.nailton.consultas.domain.usecases.UpdateConsultasUseCase
import com.nailton.consultas.domain.usecases.UpdateDocumentUseCase
import com.nailton.consultas.presentation.configmodel.ViewModelFactory
import com.nailton.consultas.presentation.dependencyinjection.annotations.ConsultaScope
import dagger.Module
import dagger.Provides

@Module
class ConsultaModule {

    @ConsultaScope
    @Provides
    fun provideViewModelFactory(
        getConsultaUseCase: GetConsultaUseCase,
        updateConsultasUseCase: UpdateConsultasUseCase,
        loginUseCase: LoginUseCase,
        persistUseCase: PersistUseCase,
        outApplicationUseCase: OutApplicationUseCase,
        createQueryUseCase: CreateQueryUseCase,
        deleteQueryUseCase: DeleteQueryUseCase,
        updateDocumentUseCase: UpdateDocumentUseCase
    ): ViewModelFactory {
        return ViewModelFactory(
            getConsultaUseCase,
            updateConsultasUseCase,
            loginUseCase,
            persistUseCase,
            outApplicationUseCase,
            createQueryUseCase,
            deleteQueryUseCase,
            updateDocumentUseCase
        )
    }
}