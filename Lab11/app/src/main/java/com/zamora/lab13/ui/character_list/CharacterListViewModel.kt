package com.zamora.lab13.ui.character_list

import androidx.lifecycle.ViewModel
import com.zamora.lab13.datasource.model.CharacterEntity
import com.zamora.lab13.datasource.repository.CharacterRepository
import com.zamora.lab13.datasource.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val repository: CharacterRepository
): ViewModel() {

    private val _screenState: MutableStateFlow<ListUiState> = MutableStateFlow(ListUiState.Empty)
    val screenState: StateFlow<ListUiState> = _screenState

    sealed interface ListUiState {
        object Empty: ListUiState
        object Loading: ListUiState
        class Success(val data: List<CharacterEntity>): ListUiState
        class Error(val message: String): ListUiState
    }

    suspend fun getCharactersList(){
        //Emitir estado loading
        val response = repository.getAllCharacters()

        when (response){
            is Resource.Success -> TODO()
            is Resource.Error -> TODO()
        }
    }
}