package com.nailton.consultas.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.nailton.consultas.data.datasource.ConsultaCacheDataSource
import com.nailton.consultas.data.datasource.ConsultaLocalDataSource
import com.nailton.consultas.domain.repository.ConsultaRepository
import kotlinx.coroutines.tasks.await

class ConsultaRepositoryImplementation (
    private val consultaLocalDataSource: ConsultaLocalDataSource,
    private val consultaCacheDataSource: ConsultaCacheDataSource
): ConsultaRepository {
    private var firebaseAuth: FirebaseAuth = Firebase.auth
    private lateinit var firebaseUser: FirebaseUser
    private val db = FirebaseFirestore.getInstance()
    private val collectionReference = db.collection("Consultas")
    var currentUser = firebaseAuth.currentUser

    override suspend fun getConsultas(): List<Consulta>? {
        return getConsultasFromCache()
    }

    override suspend fun updateConsultas(): List<Consulta>? {
        val newListConsultas = getConsultasFromFireabase()
        consultaLocalDataSource.deleteConsultasDB()
        consultaLocalDataSource.saveConsultasDB(newListConsultas)
        consultaCacheDataSource.saveConsultasToCache(newListConsultas)
        return newListConsultas
    }

    override suspend fun login(email: String, password: String): Boolean {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    return@addOnSuccessListener
                }.await().user
            return true
        } catch (_: Exception) {
            return false
        }
    }

    override suspend fun persistLogin(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override suspend fun outApplication(): FirebaseAuth? {
        return firebaseAuth
    }

    fun getConsultasFromFireabase(): List<Consulta> {
        lateinit var consultaList: List<Consulta>

        collectionReference.whereEqualTo("userId", firebaseUser.uid)
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (indices in it) {
                        val consulta = Consulta(
                            indices.data["userId"].toString(),
                            indices.data["email"].toString(),
                            indices.data["pacienteEmail"].toString(),
                            indices.data["pacienteNome"].toString(),
                            indices.data["titulo"].toString(),
                            indices.data["descricao"].toString(),
                            indices.data["isMedico"] as Boolean
                        )
                        consultaList = listOf(consulta)
                    }
                }
            }
        return consultaList
    }

    suspend fun getConsultasFromRoom(): List<Consulta> {
        lateinit var consultaList: List<Consulta>
        try {
            consultaList = consultaLocalDataSource.getConsultasFromDB()
        } catch (_: Exception) {}

        if (consultaList.isNotEmpty()) {
            return consultaList
        } else {
            consultaList = getConsultasFromFireabase()
            consultaLocalDataSource.saveConsultasDB(consultaList)
        }
        return consultaList
    }

    private suspend fun getConsultasFromCache(): List<Consulta>? {
        lateinit var consultaList: List<Consulta>

        try {
            consultaList = consultaCacheDataSource.getConsultasFromCache()
        } catch (_: Exception) {}

        if (consultaList.isNotEmpty()) {
            return consultaList
        } else {
            consultaList = getConsultasFromRoom()
            consultaCacheDataSource.saveConsultasToCache(consultaList)
        }
        return consultaList
    }
}