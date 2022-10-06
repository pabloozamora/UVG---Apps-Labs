package com.zamora.lab11.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.zamora.lab11.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var emailInput: TextInputLayout
    private lateinit var passInput: TextInputLayout
    private lateinit var loginButton: Button


    companion object{
        public val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "login-data")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Verificar si el correo ya está guardado
        CoroutineScope(Dispatchers.Main).launch {
            checkLogged()
        }

        emailInput = view.findViewById(R.id.emailInput)
        passInput = view.findViewById(R.id.passwordInput)
        loginButton = view.findViewById(R.id.login_button)

        initListeners()
    }

    private fun initListeners() {
        loginButton.setOnClickListener {
            if (emailInput.editText!!.text.toString() == "zam21780@uvg.edu.gt" && emailInput.editText!!.text.toString() == passInput.editText!!.text.toString()){
                CoroutineScope(Dispatchers.IO).launch {
                    saveEmail(emailInput.editText!!.text.toString())

                }
                val action = LoginFragmentDirections.actionLoginFragmentToCharactersListFragment()
                requireView().findNavController().navigate(action)
            }
            else{
                Toast.makeText(activity, "Datos incorrectos, intente de nuevo", Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun saveEmail(email: String) {
        val dataStoreKey = stringPreferencesKey("email")
        requireContext().dataStore.edit { settings ->
            settings[dataStoreKey] = email
        }
    }

    private suspend fun checkLogged(){
        val dataStoreKey = stringPreferencesKey("email")
        val preferences = requireContext().dataStore.data.first()
        if (preferences[dataStoreKey] == "zam21780@uvg.edu.gt"){
            val action = LoginFragmentDirections.actionLoginFragmentToCharactersListFragment()
            println("loggeado")
            requireView().findNavController().navigate(action)
        }
    }

}