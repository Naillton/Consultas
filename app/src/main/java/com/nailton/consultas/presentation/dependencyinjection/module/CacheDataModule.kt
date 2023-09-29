package com.nailton.consultas.presentation.dependencyinjection.module

import com.nailton.consultas.data.datasource.ConsultaCacheDataSource
import com.nailton.consultas.data.datasourceimplementation.ConsultaCacheDataSourceImplementation
import com.nailton.consultas.data.datasourceimplementation.ConsultaLocalDataSourceImplementation
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheDataModule {

    @Singleton
    @Provides
    fun provideConsultaCacheData(): ConsultaCacheDataSource {
        return ConsultaCacheDataSourceImplementation()
    }
}