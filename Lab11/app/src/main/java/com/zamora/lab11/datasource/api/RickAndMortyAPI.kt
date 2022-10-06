package com.zamora.lab11.datasource.api

import com.zamora.lab11.datasource.model.AllCharactersResponse
import com.zamora.lab11.datasource.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyAPI {

    @GET("/api/character")
    fun getCharacters(): Call<AllCharactersResponse>

    @GET("/api/character/{id}")
    fun getCharacter(
        @Path("id") id: Int
    ): Call<Character>
}