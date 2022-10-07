package com.zamora.lab11.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val gender: String,
    val origin: String,
    val episodes: Int,
    val image: String
        )