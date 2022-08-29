package com.zamora.lab7

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var updateButton: Button
    private val args: ProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textInputLayout = view.findViewById(R.id.textInputLayout)
        updateButton = view.findViewById(R.id.updateProfile_button)


        textInputLayout.hint = args.email

        initListeners()
    }

    private fun initListeners() {
        updateButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            requireView().findNavController().navigate(action)
        }
    }
}