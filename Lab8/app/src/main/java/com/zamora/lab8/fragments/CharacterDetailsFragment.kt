package com.zamora.lab8.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.zamora.lab8.R

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private lateinit var characterImage: ImageView
    private lateinit var characterSpecies: TextView
    private lateinit var characterName: TextView
    private lateinit var characterStatus: TextView
    private lateinit var characterGender: TextView

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterImage = view.findViewById(R.id.character_details_image)
        characterSpecies = view.findViewById(R.id.character_details_species)
        characterName = view.findViewById(R.id.character_details_name)
        characterStatus = view.findViewById(R.id.character_details_status)
        characterGender = view.findViewById(R.id.character_details_gender)

        characterImage.load(args.image){
            transformations(CircleCropTransformation())
            error(R.drawable.ic_error)
            diskCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
        }
        characterName.text = args.name
        characterSpecies.text = args.species
        characterStatus.text = args.status
        characterGender.text = args.gender

    }

}