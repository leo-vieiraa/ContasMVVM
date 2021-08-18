package com.example.contasmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contasmvvm.utils.replaceView
import com.example.contasmvvm.view.ContentFragment
import com.example.contasmvvm.view.SignInFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (FirebaseAuth.getInstance().currentUser != null) {
            replaceView(ContentFragment.newInstance())
        } else {
            replaceView(SignInFragment.newInstance())
        }

    }
}