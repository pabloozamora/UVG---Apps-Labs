package com.zamora.lab7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var loginButton: Button
    private lateinit var emailInput: TextInputEditText
    private lateinit var signUpText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton = view.findViewById(R.id.login_button)
        emailInput = view.findViewById(R.id.editText)
        signUpText = view.findViewById(R.id.signUpText)

        initListeners()
    }

    private fun initListeners(){
        loginButton.setOnClickListener {

            if (emailInput.text.toString() == "jcdurini@uvg.edu.gt"){
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(
                    email = "jcdurini@uvg.edu.gt"
                )

                requireView().findNavController().navigate(action)
            }
            else{
                Toast.makeText(requireActivity(), "El email ingresado no es correcto", Toast.LENGTH_LONG).show()
            }
        }

        signUpText.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToNewAccountFragment()
            requireView().findNavController().navigate(action)
        }
    }
}