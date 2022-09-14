package com.zamora.lab9.datasource.model

data class AllCharactersResponse(
    val info: Info,
    val results: MutableList<Character>
)