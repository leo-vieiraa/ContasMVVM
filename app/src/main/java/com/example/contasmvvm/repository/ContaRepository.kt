package com.example.contasmvvm.repository

import com.example.contasmvvm.model.Conta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ContaRepository {

    private val dataBase = Firebase.firestore
//    private val dataBase = FirebaseFirestore.getInstance()

    fun fetchContas(callback: (List<Conta>?, String?) -> Unit) {
        dataBase.collection("contas")
            .get()
            .addOnSuccessListener { result ->

                val listOf = arrayListOf<Conta>()
                result.forEach {
                    val conta = Conta.fromData(it)
                    listOf.add(conta)
                }
                callback(listOf, null)

            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

}