package com.nailton.consultas.presentation.dependencyinjection.module

import com.nailton.consultas.domain.repository.ConsultaRepository
import com.nailton.consultas.domain.usecases.GetConsultaUseCase
import com.nailton.consultas.domain.usecases.LoginUseCase
import com.nailton.consultas.domain.usecases.OutApplicationUseCase
import com.nailton.consultas.domain.usecases.PersistUseCase
import com.nailton.consultas.domain.usecases.UpdateConsultasUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    fun provideGetConsultaUseCase(consultaRepository: ConsultaRepository): GetConsultaUseCase {
        return GetConsultaUseCase(consultaRepository)
    }

    @Provides
    fun provideUpdateUseCase(consultaRepository: ConsultaRepository): UpdateConsultasUseCase {
        return UpdateConsultasUseCase(consultaRepository)
    }

    @Provides
    fun provideLoginUseCase(consultaRepository: ConsultaRepository): LoginUseCase {
        return LoginUseCase(consultaRepository)
    }

    @Provides
    fun persistUseCase(consultaRepository: ConsultaRepository): PersistUseCase {
        return PersistUseCase(consultaRepository)
    }

    @Provides
    fun outUseCase(consultaRepository: ConsultaRepository): OutApplicationUseCase {
        return OutApplicationUseCase(consultaRepository)
    }
}