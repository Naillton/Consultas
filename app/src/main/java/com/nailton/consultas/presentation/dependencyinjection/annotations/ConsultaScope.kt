package com.nailton.consultas.presentation.dependencyinjection.annotations

import javax.inject.Scope
import kotlin.annotation.Retention

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ConsultaScope()
