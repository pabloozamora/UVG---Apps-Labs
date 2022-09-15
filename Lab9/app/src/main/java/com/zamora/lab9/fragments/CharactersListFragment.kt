package com.zamora.lab9.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.zamora.lab9.R
import com.zamora.lab9.adapters.CharacterAdapter
import com.zamora.lab9.datasource.api.RetrofitInstance
import com.zamora.lab9.datasource.model.AllCharactersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.zamora.lab9.datasource.model.Character

class CharactersListFragment : Fragment(R.layout.fragment_characters_list), CharacterAdapter.RecyclerCharacterClickHandler {

    private lateinit var recyclerView: RecyclerView
    private lateinit var toolBar: MaterialToolbar
    private lateinit var charactersList: MutableList<Character>
    private lateinit var adapter: CharacterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_characters_list)
        toolBar = requireActivity().findViewById(R.id.toolbar_main_activity)
        connectToApi()
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

    private fun connectToApi() {
        RetrofitInstance.api.getCharacters().enqueue(object : Callback<AllCharactersResponse> {
            override fun onResponse(
                call: Call<AllCharactersResponse>,
                response: Response<AllCharactersResponse>
            ){
                if (response.isSuccessful){
                    println("Successfully obtained characters list")
                    charactersList = response.body()!!.results
                    setuUpRecycler()
                }
            }

            override fun onFailure(call: Call<AllCharactersResponse>, t: Throwable) {
                println("Connection failed")
                Toast.makeText(activity, "No fue posible obtener la lista de personajes. Revisa tu conexión a internet.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setuUpRecycler() {
        adapter = CharacterAdapter(charactersList, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    override fun onCharacterClicked(character: Character) {
        val action = CharactersListFragmentDirections.actionCharactersListFragmentToCharacterDetailsFragment2(
            character.id
        )

        requireView().findNavController().navigate(action)
    }

}