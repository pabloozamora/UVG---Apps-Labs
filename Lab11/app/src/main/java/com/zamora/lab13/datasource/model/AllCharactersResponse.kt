package com.zamora.lab13.datasource.model

data class AllCharactersResponse(
    val info: Info,
    val results: MutableList<Character>
)