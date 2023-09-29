package com.nailton.consultas.presentation.dependencyinjection.module

import android.content.Context
import com.nailton.consultas.presentation.dependencyinjection.interfaces.ConsultaSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [ConsultaSubComponent::class])
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContextApplication(): Context {
        return context.applicationContext
    }

}