package com.nailton.consultas.presentation.dependencyinjection.module

import com.nailton.consultas.data.datasource.ConsultaLocalDataSource
import com.nailton.consultas.data.datasourceimplementation.ConsultaLocalDataSourceImplementation
import com.nailton.consultas.data.db.ConsultaDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun providesLocalData(consultaDatabase: ConsultaDatabase): ConsultaLocalDataSource {
        return ConsultaLocalDataSourceImplementation(consultaDatabase.consultaDAO())
    }
}