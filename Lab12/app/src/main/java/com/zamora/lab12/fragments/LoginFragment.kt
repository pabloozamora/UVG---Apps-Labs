package com.zamora.lab12.fragments

import android.content.Context
import android.os.Bundle
import android.se.omapi.Session
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import com.zamora.lab12.R
import com.zamora.lab12.databinding.FragmentLoginBinding
import com.zamora.lab12.viewmodels.SessionViewModel


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val sessionViewModel: SessionViewModel by viewModels()

    companion object{
        public val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login-data")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

    }

    private fun setObservables(){

    }

    private fun setListeners(){
        binding.loginButton.setOnClickListener {
            sessionViewModel.triggerValidAuthToken(
                binding.emailInput.editText.toString(),
                binding.passwordInput.editText.toString())
        }
    }
}