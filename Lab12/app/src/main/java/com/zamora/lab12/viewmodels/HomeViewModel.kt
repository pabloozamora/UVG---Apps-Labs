package com.zamora.lab12.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val defaultMessage = "Selecciona una opción"
    private val successMessage = "¡Operación exitosa!"
    private val failureMessage = "¡Operación fallida!"
    private val emptyMessage = "Sin resultados"

    private val _uiState: MutableStateFlow<HomeFragmentState> = MutableStateFlow(HomeFragmentState.Default(defaultMessage))
    val uiState: StateFlow<HomeFragmentState> = _uiState

    sealed class HomeFragmentState {
        class Default(val defaultMessage: String): HomeFragmentState()
        class Success(val successMessage: String): HomeFragmentState()
        class Failure(val failureMessage: String): HomeFragmentState()
        class Empty(val emptyMessage: String): HomeFragmentState()
        object Loading: HomeFragmentState()
    }

    fun setDefaultState(){
        viewModelScope.launch {
            _uiState.value = HomeFragmentState.Loading
            delay(2000L)
            _uiState.value = HomeFragmentState.Default(defaultMessage)
        }
    }

    fun setSuccessState(){
        viewModelScope.launch {
            _uiState.value = HomeFragmentState.Loading
            delay(2000L)
            _uiState.value = HomeFragmentState.Success(successMessage)
        }
    }

    fun setFailureState(){
        viewModelScope.launch {
            _uiState.value = HomeFragmentState.Loading
            delay(2000L)
            _uiState.value = HomeFragmentState.Failure(failureMessage)
        }
    }

    fun setEmptyState(){
        viewModelScope.launch {
            _uiState.value = HomeFragmentState.Loading
            delay(2000L)
            _uiState.value = HomeFragmentState.Empty(emptyMessage)
        }
    }


}