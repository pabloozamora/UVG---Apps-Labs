package com.zamora.lab13.datasource.localsource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zamora.lab13.datasource.model.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
abstract class Database : RoomDatabase(){
    abstract fun characterDao(): CharacterDao
}