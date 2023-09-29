package com.nailton.consultas.presentation.dependencyinjection.module

import com.google.firebase.auth.FirebaseAuth
import com.nailton.consultas.data.ConsultaRepositoryImplementation
import com.nailton.consultas.data.datasource.ConsultaCacheDataSource
import com.nailton.consultas.data.datasource.ConsultaLocalDataSource
import com.nailton.consultas.domain.repository.ConsultaRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideConsultaRepository(
        consultaLocalDataSource: ConsultaLocalDataSource,
        consultaCacheDataSource: ConsultaCacheDataSource
    ): ConsultaRepository {
        return ConsultaRepositoryImplementation(
            consultaLocalDataSource,
            consultaCacheDataSource
        )
    }
}