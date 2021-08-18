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
import com.example.contasmvvm.MainActivity
import com.example.contasmvvm.R
import com.example.contasmvvm.utils.replaceView
import com.example.contasmvvm.viewmodel.SignInViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser

class SignInFragment : Fragment(R.layout.sign_in_fragment) {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var viewModel: SignInViewModel

    private val observerUser = Observer<FirebaseUser> {
        requireActivity().replaceView(ContentFragment.newInstance())
    }

    private val observerError = Observer<String> {
        Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        viewModel.error.observe(viewLifecycleOwner, observerError)
        viewModel.user.observe(viewLifecycleOwner, observerUser)


        /**
         * Add eventos nos componentes da tela
         */
        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            val inputEmail = view.findViewById<EditText>(R.id.inputEmailEditText)
            val inputPassword = view.findViewById<EditText>(R.id.inputPasswordEditText)
            if (!inputEmail.text.isNullOrEmpty() && !inputPassword.text.isNullOrEmpty()) {
                viewModel.signIn(
                    inputEmail.text.toString(),
                    inputPassword.text.toString()
                )
            }
        }

        view.findViewById<View>(R.id.newAccountTextView).setOnClickListener {
            (requireActivity() as? MainActivity)?.replaceView(SignUpFragment.newInstance())
        }
    }

}