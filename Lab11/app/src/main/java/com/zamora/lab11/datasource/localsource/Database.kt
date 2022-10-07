package com.zamora.lab11.datasource.localsource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zamora.lab11.datasource.model.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
abstract class Database : RoomDatabase(){
    abstract fun characterDao(): CharacterDao
}