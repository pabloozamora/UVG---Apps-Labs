package com.zamora.lab13.datasource.repository

import com.zamora.lab13.datasource.model.CharacterEntity
import com.zamora.lab13.datasource.util.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getAllCharacters(): Resource<List<CharacterEntity>>
    suspend fun deleteAllCharacters(): Resource<Unit>
    suspend fun getCharacter(id: Int): Resource<Character?>
    suspend fun updateCharacter(character: Character): Resource<Unit>
    suspend fun deleteCharacter(id: Int): Resource<Unit>
}