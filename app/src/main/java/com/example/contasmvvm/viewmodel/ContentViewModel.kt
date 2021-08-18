package com.example.contasmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contasmvvm.model.Bill
import com.example.contasmvvm.repository.BillRepository

class ContentViewModel : ViewModel() {

    private val _contas = MutableLiveData<List<Bill>>()
    val contas: LiveData<List<Bill>> = _contas

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    val billsRepository = BillRepository()

    fun fetchContas() {
        billsRepository.fetchBills { contas, error ->
            if (error != null) {
                _error.value = error
            } else {
                _contas.value = contas
            }
        }
    }

    fun addBill(name: String, price: Double?) {
        Bill(null, name, price).apply {
            billsRepository.addBill(this) { bill, error ->
                if (error != null) {
                    _error.value = error
                } else {
                    fetchContas()
                }
            }
        }
    }

}