package com.zamora.lab13.datasource.repository

import com.zamora.lab13.datasource.model.CharacterEntity
import com.zamora.lab13.datasource.util.DataState

interface CharacterRepository {
    suspend fun getAll(): DataState<List<CharacterEntity>>
    suspend fun deleteAllCharacters()
    suspend fun getCharacter(id: Int): DataState<CharacterEntity?>
    suspend fun updateCharacter(character: CharacterEntity)
    suspend fun deleteCharacter(id: Int)

}