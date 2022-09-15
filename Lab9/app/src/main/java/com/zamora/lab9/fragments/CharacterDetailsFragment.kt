package com.zamora.lab9.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.zamora.lab9.R
import com.zamora.lab9.datasource.api.RetrofitInstance
import com.zamora.lab9.datasource.model.AllCharactersResponse
import com.zamora.lab9.datasource.model.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private lateinit var characterImage: ImageView
    private lateinit var characterSpecies: TextView
    private lateinit var characterName: TextView
    private lateinit var characterStatus: TextView
    private lateinit var characterGender: TextView
    private lateinit var characterOrigin: TextView
    private lateinit var characterAppearances: TextView
    private lateinit var characterDisplayed: Character

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterImage = view.findViewById(R.id.character_details_image)
        characterSpecies = view.findViewById(R.id.character_details_species)
        characterName = view.findViewById(R.id.character_details_name)
        characterStatus = view.findViewById(R.id.character_details_status)
        characterGender = view.findViewById(R.id.character_details_gender)
        characterOrigin = view.findViewById(R.id.character_details_origin)
        characterAppearances = view.findViewById(R.id.character_details_appearances)

        RetrofitInstance.api.getCharacter(args.id).enqueue(object : Callback<Character> {
            override fun onResponse(
                call: Call<Character>,
                response: Response<Character>
            ){
                if (response.isSuccessful){
                    println("Character Details obtained successfully")
                    characterDisplayed = response.body()!!
                    setUpCharacter()
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                println("Connection failed")
                Toast.makeText(activity, "No fue posible obtener los detalles del personaje. Revisa tu conexión a internet.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setUpCharacter() {
        characterImage.load(characterDisplayed.image){
            transformations(CircleCropTransformation())
            error(R.drawable.ic_error)
            diskCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
        }

        characterName.text = characterDisplayed.name
        characterSpecies.text = characterDisplayed.species
        characterStatus.text = characterDisplayed.status
        characterGender.text = characterDisplayed.gender
        characterOrigin.text = characterDisplayed.origin.name
        characterAppearances.text = characterDisplayed.episode.size.toString()
    }

}