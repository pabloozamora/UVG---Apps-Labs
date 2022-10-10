package com.zamora.lab12.fragments

import android.content.Context
import android.os.Bundle
import android.se.omapi.Session
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.zamora.lab12.R
import com.zamora.lab12.databinding.FragmentLoginBinding
import com.zamora.lab12.viewmodels.SessionViewModel
import kotlinx.coroutines.flow.collectLatest


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val sessionViewModel: SessionViewModel by activityViewModels()

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

        binding.progressBarLoginFragment.isVisible = false
        setObservables()
        setListeners()

    }

    private fun setObservables(){
        lifecycleScope.launchWhenStarted {
            sessionViewModel.validAuthToken.collectLatest { authToken ->
                println(authToken.auth)
                if (authToken.auth == false && binding.emailInput.editText!!.text.toString() != ""){
                    Toast.makeText(activity, "Datos incorrectos, intente de nuevo", Toast.LENGTH_LONG).show()
                    binding.loginButton.isVisible = true
                    binding.progressBarLoginFragment.isVisible = false
                }
                else if (authToken.auth == true){
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    requireView().findNavController().navigate(action)
                }
            }
        }
    }

    private fun setListeners(){
        binding.loginButton.setOnClickListener {
            binding.loginButton.isVisible = false
            binding.progressBarLoginFragment.isVisible = true
            sessionViewModel.login(
                binding.emailInput.editText!!.text.toString(),
                binding.passwordInput.editText!!.text.toString())
        }
    }
}