package com.zamora.lab10.datasource.model

data class AllCharactersResponse(
    val info: Info,
    val results: MutableList<Character>
)