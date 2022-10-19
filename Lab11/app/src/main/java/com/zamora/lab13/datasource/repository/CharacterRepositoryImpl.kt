package com.zamora.lab13.datasource.repository

import com.zamora.lab13.datasource.api.RickAndMortyAPI
import com.zamora.lab13.datasource.localsource.CharacterDao
import com.zamora.lab13.datasource.model.CharacterEntity
import com.zamora.lab13.datasource.model.mapToModel
import com.zamora.lab13.datasource.util.DataState
import com.zamora.lab13.datasource.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRepositoryImpl(
    private val characterDao: CharacterDao,
    private var api: RickAndMortyAPI
): CharacterRepository {

    override suspend fun getAllCharacters(): Resource<List<CharacterEntity>> {
        val localCharacters = characterDao.getAllCharacters()
        if (localCharacters.isEmpty()){
            try{
                val remoteCharacters = api.getCharacters().results
                val charactersToStore = remoteCharacters.map { dto -> dto.mapToModel() }
                characterDao.insertAll(charactersToStore)
                Resource.Success(charactersToStore)
            }catch (e: Exception){
                Resource.Error(e.message ?: "")
            }
        } else{
            Resource.Success(localCharacters)
        }
    }

    override fun deleteAllCharacters(): Flow<DataState<Int>> = flow {
        //Retornar Flow
        //Emitir Loading
        //Obtener cuantos registros se borraron
        //Si se borraron mas de 0, success
        //Si se borraron 0, error
    }

    override fun getCharacter(id: Int): DataState<CharacterEntity?> {
        TODO("Not yet implemented")
        //Retornar Flow
        //Emitir Loading
        //Obtener de base de datos
        //Si no hay registro, error
        //Si sí hay registro, update

    }

    override fun updateCharacter(character: CharacterEntity) {
        //Retornar Flow
        //Emitir Loading
        //Guardar en base de datos local
        //Si retorna 0, error
        //Si retorna 1, success
    }

    override fun deleteCharacter(id: Int) {
        //Retornar Flow
        //Emitir Loading
        //Borrar de base de datos local
        //Si retorna 0, error
        //Si retorna 1, success
    }
}