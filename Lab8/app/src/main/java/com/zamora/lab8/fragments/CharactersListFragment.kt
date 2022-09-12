package com.zamora.lab8.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zamora.lab8.R
import com.zamora.lab8.adapters.CharacterAdapter
import com.zamora.lab8.database.Character
import com.zamora.lab8.database.Database

class CharactersListFragment : Fragment(R.layout.fragment_characters_list), CharacterAdapter.RecyclerCharacterClickHandler {

    private lateinit var recyclerView: RecyclerView
    private lateinit var charactersList: MutableList<Character>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_characters_list)
        setUpRecycler()
    }

    private fun setUpRecycler() {
        charactersList = Database.getCharacters()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = CharacterAdapter(charactersList, this)
    }

    override fun onCharacterClicked(character: Character) {
        val action = CharactersListFragmentDirections.actionCharactersListFragmentToCharacterDetailsFragment2(
            character.image,
            character.name,
            character.status,
            character.gender,
            character.species
        )

        requireView().findNavController().navigate(action)
    }

}