package com.nailton.consultas.data

import android.util.Log
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
    private var db = FirebaseFirestore.getInstance()
    private var collectionReference = db.collection("Consultas")

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

    override suspend fun createQuery(
        pacienteEmail: String,
        pacienteNome: String,
        titulo: String,
        descricao: String
    ): Boolean? {
        val consulta = firebaseAuth.currentUser?.let {
            it.email?.let { it1 ->
                Consulta(
                    userId = it.uid,
                    email = it1,
                    pacienteEmail,
                    pacienteNome,
                    titulo,
                    descricao,
                    isMedico = true
                )
            }
        }
        var isTrue: Boolean = false
        try {
            if (consulta != null) {
                collectionReference.add(consulta)
                    .addOnSuccessListener {
                        return@addOnSuccessListener
                    }.addOnFailureListener {
                        return@addOnFailureListener
                    }
                isTrue = true
            }
        } catch (_: Exception) {
            isTrue = false
        }
        return isTrue
    }

    override suspend fun deleteQuery(consulta: Consulta): Boolean {
        val collection = collectionReference
            .whereEqualTo("userId", consulta.userId)
            .whereEqualTo("pacienteEmail", consulta.pacienteEmail)
            .get()
            .await()
        var isTrue: Boolean = false
        if (collection.documents.isNotEmpty()) {
            for (document in collection) {
                isTrue = try {
                    collectionReference.document(document.id).delete()
                    true
                } catch (_: Exception) {
                    false
                }
            }
        }
        return isTrue
    }

    suspend fun getConsultasFromFireabase(): List<Consulta> {
        lateinit var consultaList: List<Consulta>
        val consultaMutableList: MutableList<Consulta> = arrayListOf()
        try {
            collectionReference.whereEqualTo("userId", firebaseAuth.currentUser?.uid)
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
                                indices.data["medico"] as Boolean
                            )
                            consultaMutableList.add(consulta)
                        }
                    }
                }.await()
        } catch (_: Exception) {}
        consultaList = consultaMutableList
        return consultaList
    }

    suspend fun getConsultasFromRoom(): List<Consulta> {
        lateinit var consultaList: List<Consulta>
        try {
            consultaList = consultaLocalDataSource.getConsultasFromDB()
        } catch (_: Exception) { }

        if (consultaList.isNotEmpty()) {
            return consultaList
        } else {
            consultaList = getConsultasFromFireabase()
            consultaLocalDataSource.saveConsultasDB(consultaList)
        }
        return consultaList
    }

    suspend fun getConsultasFromCache(): List<Consulta>? {
        lateinit var consultaList: List<Consulta>
        try {
            consultaList = consultaCacheDataSource.getConsultasFromCache()
        } catch (_: Exception) { }

        if (consultaList.isNotEmpty()) {
            return consultaList
        } else {
            consultaList = getConsultasFromRoom()
            consultaCacheDataSource.saveConsultasToCache(consultaList)
        }
        return consultaList
    }
}