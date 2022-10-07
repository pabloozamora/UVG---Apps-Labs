package com.zamora.lab11.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.zamora.lab11.R
import com.zamora.lab11.adapters.CharacterAdapter
import com.zamora.lab11.datasource.api.RetrofitInstance
import com.zamora.lab11.datasource.model.AllCharactersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.zamora.lab11.datasource.model.Character
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.room.Room
import com.zamora.lab11.datasource.localsource.Database
import com.zamora.lab11.datasource.model.CharacterEntity
import com.zamora.lab11.fragments.LoginFragment.Companion.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersListFragment : Fragment(R.layout.fragment_characters_list), CharacterAdapter.RecyclerCharacterClickHandler {

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolBar: MaterialToolbar
    private lateinit var charactersList: MutableList<Character>
    private lateinit var adapter: CharacterAdapter
    private lateinit var entity: CharacterEntity
    private lateinit var database: Database
    private lateinit var characterEntitiesList: MutableList<CharacterEntity>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_characters_list)
        toolBar = requireActivity().findViewById(R.id.toolbar_main_activity)
        characterEntitiesList = ArrayList()
        database = Room.databaseBuilder(
            requireContext(),
            Database::class.java,
            "bdname"
        ).build()


        getCharacters()
        setUpListeners()
    }

    private fun setUpListeners() {
        toolBar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.sort_az_button -> {
                    characterEntitiesList.sortBy { character -> character.name }
                    adapter.notifyDataSetChanged()
                }
                R.id.sort_za_button ->{
                    characterEntitiesList.sortByDescending { character -> character.name }
                    adapter.notifyDataSetChanged()
                }
                R.id.logout_button ->{
                    CoroutineScope(Dispatchers.IO).launch{
                        database.characterDao().deleteAllCharacters()
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        logout()
                    }
                }
                R.id.sync_button ->{
                    CoroutineScope(Dispatchers.IO).launch{
                        database.characterDao().deleteAllCharacters()
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        connectToApi()
                    }
                }
            }

            true
        }
    }

    private fun connectToApi() {
        RetrofitInstance.api.getCharacters().enqueue(object : Callback<AllCharactersResponse> {
            override fun onResponse(
                call: Call<AllCharactersResponse>,
                response: Response<AllCharactersResponse>
            ){
                if (response.isSuccessful){
                    characterEntitiesList.clear()
                    println("Successfully obtained characters list")
                    charactersList = response.body()!!.results
                    for (character in charactersList){
                        entity = CharacterEntity(character.id,
                            character.name,
                            character.species,
                            character.status,
                            character.gender,
                            character.origin.name,
                            character.episode.size,
                            character.image)
                        characterEntitiesList.add(entity)
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        for (character in characterEntitiesList){
                            database.characterDao().createCharacter(character)
                        }
                    }
                    CoroutineScope(Dispatchers.Main).launch{
                        setUpRecycler()
                    }
                }
            }

            override fun onFailure(call: Call<AllCharactersResponse>, t: Throwable) {
                println("Connection failed")
                Toast.makeText(activity, "No fue posible obtener la lista de personajes. Revisa tu conexión a internet.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getCharacters(){
        CoroutineScope(Dispatchers.IO).launch {
            val characters = database.characterDao().getAllCharacters()
            if (characters.isEmpty()){
                connectToApi()
            }
            else{
                characterEntitiesList.clear()
                characterEntitiesList.addAll(characters)
                CoroutineScope(Dispatchers.Main).launch {
                    setUpRecycler()
                }
            }
        }
    }


    private fun setUpRecycler() {
        adapter = CharacterAdapter(characterEntitiesList, this@CharactersListFragment)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

    }

    override fun onCharacterClicked(character: CharacterEntity) {
        val action = CharactersListFragmentDirections.actionCharactersListFragmentToCharacterDetailsFragment2(
            character.id
        )

        requireView().findNavController().navigate(action)
    }

    private suspend fun logout(){
        val dataStoreKey = stringPreferencesKey("email")
        requireContext().dataStore.edit { settings ->
            settings[dataStoreKey] = "empty"
        }

        CoroutineScope(Dispatchers.IO).launch{
            database.characterDao().deleteAllCharacters()
        }

        CoroutineScope(Dispatchers.Main).launch {
            val action = CharactersListFragmentDirections.actionCharactersListFragmentToLoginFragment()
            requireView().findNavController().navigate(action)
        }
    }

}