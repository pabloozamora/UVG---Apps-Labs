package com.zamora.lab11.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.zamora.lab11.R
import com.zamora.lab11.datasource.api.RetrofitInstance
import com.zamora.lab11.datasource.localsource.Database
import com.zamora.lab11.datasource.model.Character
import com.zamora.lab11.datasource.model.CharacterEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private lateinit var characterImage: ImageView
    private lateinit var characterSpecies: TextInputLayout
    private lateinit var characterName: TextInputLayout
    private lateinit var characterStatus: TextInputLayout
    private lateinit var characterGender: TextInputLayout
    private lateinit var characterOrigin: TextInputLayout
    private lateinit var characterAppearances: TextInputLayout
    private lateinit var saveButton: Button
    private lateinit var characterFromApi: Character
    private lateinit var characterDisplayed: CharacterEntity
    private lateinit var toolBar: MaterialToolbar
    private lateinit var database: Database

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterImage = view.findViewById(R.id.character_details_image)
        characterSpecies = view.findViewById(R.id.textInput_characterDetailsFragment_species)
        characterName = view.findViewById(R.id.textInput_characterDetailsFragment_name)
        characterStatus = view.findViewById(R.id.textInput_characterDetailsFragment_status)
        characterGender = view.findViewById(R.id.textInput_characterDetailsFragment_gender)
        characterOrigin = view.findViewById(R.id.textInput_characterDetailsFragment_origin)
        characterAppearances = view.findViewById(R.id.textInput_characterDetailsFragment_episodes)
        saveButton = view.findViewById(R.id.button_characterDetailsFragment_saveChanges)
        database = Room.databaseBuilder(
            requireContext(),
            Database::class.java,
            "bdname"
        ).build()

    }

    private fun setUpListeners(){
        toolBar.setOnMenuItemClickListener { item ->
            when (item.itemId){
                R.id.sync_button -> {
                    refreshCharacter()
                }
                R.id.delete_button -> {
                    deleteCharacter()
                }
            }
            true
        }

        saveButton.setOnClickListener {
            val episodesInt = Integer.parseInt(characterAppearances.editText.toString())
            val updatedCharacter = characterDisplayed.copy(
                name = characterName.editText.toString(),
                species = characterSpecies.editText.toString(),
                status = characterStatus.editText.toString(),
                gender = characterGender.editText.toString(),
                origin = characterOrigin.editText.toString(),
                episodes = episodesInt
            )
            CoroutineScope(Dispatchers.IO).launch {
                database.characterDao().updateCharacter(updatedCharacter)
            }
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(activity, "Datos de personaje actualizados", Toast.LENGTH_LONG).show()
                setUpCharacter()
            }
        }
    }

    private fun refreshCharacter(){
        RetrofitInstance.api.getCharacter(args.id).enqueue(object : Callback<Character> {
            override fun onResponse(
                call: Call<Character>,
                response: Response<Character>
            ){
                if (response.isSuccessful){
                    println("Character Details obtained successfully")
                    characterFromApi = response.body()!!
                    characterDisplayed = CharacterEntity(characterFromApi.id,
                        characterFromApi.name,
                        characterFromApi.species,
                        characterFromApi.status,
                        characterFromApi.gender,
                        characterFromApi.origin.name,
                        characterFromApi.episode.size,
                        characterFromApi.image)
                    setUpCharacter()
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                println("Connection failed")
                Toast.makeText(activity, "No fue posible obtener los detalles del personaje. Revisa tu conexión a internet.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun deleteCharacter(){
        CoroutineScope(Dispatchers.IO).launch {
            database.characterDao().delete(characterDisplayed)
        }
        CoroutineScope(Dispatchers.Main).launch {
            val action = CharacterDetailsFragmentDirections.actionCharacterDetailsFragment2ToCharactersListFragment()
            requireView().findNavController().navigate(action)
        }
    }

    private fun setUpCharacter() {

        CoroutineScope(Dispatchers.IO).launch {
            characterDisplayed = database.characterDao().getCharacter(args.id)
        }

        characterImage.load(characterDisplayed.image){
            transformations(CircleCropTransformation())
            error(R.drawable.ic_error)
            diskCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
        }

        characterName.editText!!.setText(characterDisplayed.name)
        characterSpecies.editText!!.setText(characterDisplayed.species)
        characterStatus.editText!!.setText(characterDisplayed.status)
        characterGender.editText!!.setText(characterDisplayed.gender)
        characterOrigin.editText!!.setText(characterDisplayed.origin)
        characterAppearances.editText!!.setText(characterDisplayed.episodes.toString())
    }

}