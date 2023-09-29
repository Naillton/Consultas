package com.nailton.consultas

import android.app.Application
import com.nailton.consultas.presentation.dependencyinjection.interfaces.AppComponent
import com.nailton.consultas.presentation.dependencyinjection.interfaces.ConsultaSubComponent
import com.nailton.consultas.presentation.dependencyinjection.interfaces.DaggerAppComponent
import com.nailton.consultas.presentation.dependencyinjection.interfaces.Injector
import com.nailton.consultas.presentation.dependencyinjection.module.AppModule

class App: Application(), Injector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

    override fun createConsultaSubComponent(): ConsultaSubComponent {
        return appComponent.consultaSubComponent().create()
    }
}