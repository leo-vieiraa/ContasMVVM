package com.example.contasmvvm.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import com.example.contasmvvm.R
import com.example.contasmvvm.utils.replaceView
import com.example.contasmvvm.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser

class SignUpFragment : Fragment(R.layout.sign_up_fragment) {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private val observerNewUser = Observer<FirebaseUser?> {
        Snackbar.make(requireView(), "Usuário criado com sucesso!!!!", Snackbar.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        /**
         * Add observers
         */
        viewModel.user.observe(viewLifecycleOwner, observerNewUser)

        /**
         * Add eventos nos componentes da tela
         */
        view.findViewById<Button>(R.id.saveButton).setOnClickListener {
            val inputEmail = view.findViewById<EditText>(R.id.inputEmailEditText)
            val inputPassword = view.findViewById<EditText>(R.id.inputPasswordEditText)
            if (!inputEmail.text.isNullOrEmpty() && !inputPassword.text.isNullOrEmpty()) {
                viewModel.createNewAccount(
                    inputEmail.text.toString(),
                    inputPassword.text.toString()
                )
            }
        }

        view.findViewById<View>(R.id.backButton).setOnClickListener {
            requireActivity().replaceView(SignInFragment.newInstance(), R.id.action_bar)
        }


    }

}