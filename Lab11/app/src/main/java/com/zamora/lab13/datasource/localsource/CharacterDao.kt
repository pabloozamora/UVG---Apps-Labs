package com.zamora.lab13.datasource.localsource

import androidx.room.*
import com.zamora.lab13.datasource.model.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characterEntity")
    suspend fun getAllCharacters(): MutableList<CharacterEntity>

    @Insert
    suspend fun createCharacter(characterEntity: CharacterEntity)

    @Delete
    suspend fun delete(characterEntity: CharacterEntity)

    @Query("SELECT * FROM CharacterEntity WHERE id = :id")
    suspend fun getCharacter(id: Int): CharacterEntity

    @Query("SELECT name FROM CharacterEntity WHERE id = :id")
    suspend fun getCharacterName(id: Int): String

    @Query("SELECT status FROM CharacterEntity WHERE id = :id")
    suspend fun getCharacterStatus(id: Int): String

    @Update
    suspend fun updateCharacter(characterEntity: CharacterEntity)

    @Query("DELETE FROM characterEntity")
    suspend fun deleteAllCharacters()

}