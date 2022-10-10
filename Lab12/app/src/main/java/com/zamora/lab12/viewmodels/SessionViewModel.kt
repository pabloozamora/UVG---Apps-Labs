package com.zamora.lab12.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionViewModel: ViewModel() {

    private val _validAuthToken: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val validAuthToken: StateFlow<Boolean> = _validAuthToken

    fun triggerValidAuthToken(email: String, pass: String){
        if (email == pass){
            _validAuthToken.value = true
        }
    }

}