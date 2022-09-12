package com.zamora.lab8.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.zamora.lab8.R
import com.zamora.lab8.activities.MainActivity
import com.zamora.lab8.adapters.CharacterAdapter
import com.zamora.lab8.database.Character
import com.zamora.lab8.database.Database

class CharactersListFragment : Fragment(R.layout.fragment_characters_list), CharacterAdapter.RecyclerCharacterClickHandler {

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolBar: MaterialToolbar
    private lateinit var charactersList: MutableList<Character>
    private lateinit var adapter: CharacterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_characters_list)
        toolBar = requireActivity().findViewById(R.id.toolbar_main_activity)
        setUpRecycler()
        setUpListeners()
    }

    private fun setUpListeners() {
        toolBar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.sort_az_button -> {
                    charactersList.sortBy { character -> character.name }
                    adapter.notifyDataSetChanged()
                }
                R.id.sort_za_button ->{
                    charactersList.sortByDescending { character -> character.name }
                    adapter.notifyDataSetChanged()
                }
            }

            true
        }
    }

    private fun setUpRecycler() {
        charactersList = Database.getCharacters()
        adapter = CharacterAdapter(charactersList, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
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