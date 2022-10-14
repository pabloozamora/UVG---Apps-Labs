package com.zamora.lab13.datasource.repository

import com.zamora.lab13.datasource.model.CharacterEntity
import com.zamora.lab13.datasource.util.DataState
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAll(): Flow<DataState<List<CharacterEntity>>>
    fun deleteAllCharacters(): Flow<DataState<Int>>
    fun getCharacter(id: Int): Flow<DataState<CharacterEntity?>>
    fun updateCharacter(character: CharacterEntity): Flow<DataState<Int>>
    fun deleteCharacter(id: Int): Flow<DataState<Int>>

}