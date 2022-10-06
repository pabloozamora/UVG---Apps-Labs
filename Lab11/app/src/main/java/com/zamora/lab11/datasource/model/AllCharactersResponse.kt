package com.zamora.lab11.datasource.model

data class AllCharactersResponse(
    val info: Info,
    val results: MutableList<Character>
)