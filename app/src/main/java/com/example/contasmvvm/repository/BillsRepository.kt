package com.example.contasmvvm.repository

import com.example.contasmvvm.model.Bill
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val BILLS_COLLECTION = "contas"

class BillRepository {

    private val dataBase = Firebase.firestore
//    private val dataBase = FirebaseFirestore.getInstance()

    fun fetchBills(callback: (List<Bill>?, String?) -> Unit) {
        dataBase.collection("contas")
            .get()
            .addOnSuccessListener { result ->

                val listOf = arrayListOf<Bill>()
                result.forEach {
                    val conta = Bill.fromData(it)
                    listOf.add(conta)
                }
                callback(listOf, null)

            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    fun addBill(bill: Bill, callback: (Bill?, String?) -> Unit) {
        dataBase.collection(BILLS_COLLECTION)
            .add(bill)
            .addOnSuccessListener {

                Bill.fromDocument(it).apply {
                    callback(this, null)
                }

            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

}