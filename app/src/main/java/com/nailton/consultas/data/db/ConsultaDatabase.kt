package com.nailton.consultas.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nailton.consultas.data.Consulta

@Database(entities = [Consulta::class], version = 1, exportSchema = false)
abstract class ConsultaDatabase: RoomDatabase() {
    abstract  fun consultaDAO(): ConsultaDao
}