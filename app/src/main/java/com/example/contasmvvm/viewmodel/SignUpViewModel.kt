package com.example.contasmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contasmvvm.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

class SignUpViewModel : ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    var user: LiveData<FirebaseUser?> = _user

    private val repository = AuthenticationRepository()

    fun createNewAccount(email: String, password: String) {
        repository.createAccountWithEmailPassword(email, password) {
            _user.value = it
        }
    }

}