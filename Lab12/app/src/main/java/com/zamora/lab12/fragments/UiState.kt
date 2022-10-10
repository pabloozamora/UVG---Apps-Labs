package com.zamora.lab12.fragments

sealed class UiState {
    class Default(val defaultMessage: String): UiState()
    class Success(val successMessage: String): UiState()
    class Failure(val failureMessage: String): UiState()
    class Empty(val emptyMessage: String): UiState()
    object Loading: UiState()
}