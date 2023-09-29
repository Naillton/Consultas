package com.nailton.consultas.presentation.dependencyinjection.interfaces

import com.nailton.consultas.presentation.dependencyinjection.module.AppModule
import com.nailton.consultas.presentation.dependencyinjection.module.CacheDataModule
import com.nailton.consultas.presentation.dependencyinjection.module.DatabaseModule
import com.nailton.consultas.presentation.dependencyinjection.module.LocalDataModule
import com.nailton.consultas.presentation.dependencyinjection.module.RepositoryModule
import com.nailton.consultas.presentation.dependencyinjection.module.UseCaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    CacheDataModule::class,
    DatabaseModule::class,
    LocalDataModule::class,
    RepositoryModule::class,
    UseCaseModule::class
])
interface AppComponent {

    fun consultaSubComponent(): ConsultaSubComponent.Factory
}