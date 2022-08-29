package com.zamora.lab7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText

class NewAccountFragment : Fragment(R.layout.fragment_new_account) {

    private lateinit var emailInput: TextInputEditText
    private lateinit var signUpButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailInput = view.findViewById(R.id.editText)
        signUpButton = view.findViewById(R.id.signUp_button)

        initListeners()
    }

    private fun initListeners() {
        signUpButton.setOnClickListener {
            val action = NewAccountFragmentDirections.actionNewAccountFragmentToHomeFragment(emailInput.text.toString())
            requireView().findNavController().navigate(action)
        }
    }


}