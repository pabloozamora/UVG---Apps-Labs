package com.zamora.lab13.datasource.repository

import com.zamora.lab13.datasource.model.CharacterEntity
import com.zamora.lab13.datasource.util.DataState

class CharacterRepositoryImpl: CharacterRepository {
    override suspend fun getAll(): DataState<List<CharacterEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllCharacters() {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacter(id: Int): DataState<CharacterEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateCharacter(character: CharacterEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCharacter(id: Int) {
        TODO("Not yet implemented")
    }
}