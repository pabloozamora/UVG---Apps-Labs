package com.zamora.lab7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var updateText: TextView
    private lateinit var updateButton: Button
    private val args: HomeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateText = view.findViewById(R.id.signUpText)
        updateButton = view.findViewById(R.id.updateProfile_button)

        updateText.text = "Hola ${args.email}, necesitamos que\nactualices tu perfil"

        initListeners()

    }

    private fun initListeners() {
        updateButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment(args.email)
            requireView().findNavController().navigate(action)
        }
    }
}