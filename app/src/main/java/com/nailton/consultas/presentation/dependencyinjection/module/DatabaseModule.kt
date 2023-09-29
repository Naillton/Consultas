package com.nailton.consultas.presentation.dependencyinjection.module

import android.content.Context
import androidx.room.Room
import com.nailton.consultas.data.db.ConsultaDao
import com.nailton.consultas.data.db.ConsultaDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideConsultaDatabase(context: Context): ConsultaDatabase {
        return Room.databaseBuilder(
            context,
            ConsultaDatabase::class.java,
            "Consultas"
        ).build()
    }

    @Singleton
    @Provides
    fun provideConsultaDAO(consultaDatabase: ConsultaDatabase): ConsultaDao {
        return consultaDatabase.consultaDAO()
    }
}