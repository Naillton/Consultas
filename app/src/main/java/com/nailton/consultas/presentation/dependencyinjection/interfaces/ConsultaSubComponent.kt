package com.nailton.consultas.presentation.dependencyinjection.interfaces

import com.nailton.consultas.MainActivity
import com.nailton.consultas.presentation.dependencyinjection.annotations.ConsultaScope
import com.nailton.consultas.presentation.dependencyinjection.module.ConsultaModule
import com.nailton.consultas.screens.LoginFragment
import com.nailton.consultas.screens.MedicoFragment
import com.nailton.consultas.screens.NovaConsultaFragment
import dagger.Subcomponent

@ConsultaScope
@Subcomponent(modules = [ConsultaModule::class])
interface ConsultaSubComponent {

    fun inject(loginFragment: LoginFragment)
    fun injectMedico(medicoFragment: MedicoFragment)
    fun injectQuery(novaConsultaFragment: NovaConsultaFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ConsultaSubComponent
    }
}