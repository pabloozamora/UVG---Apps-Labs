package com.zamora.lab13.datasource.api

import com.zamora.lab13.datasource.localsource.CharacterDao
import com.zamora.lab13.datasource.model.AllCharactersResponse
import com.zamora.lab13.datasource.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyAPI {

    @GET("/api/character")
    suspend fun getCharacters(): AllCharactersResponse

    @GET("/api/character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ): CharacterDao
}