package com.zamora.lab8.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.loadAny
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.zamora.lab8.R
import com.zamora.lab8.database.Character
import com.zamora.lab8.fragments.CharactersListFragment
import com.zamora.lab8.fragments.CharactersListFragmentDirections

class CharacterAdapter(private val dataSet: MutableList<Character>,
                       private val listener: RecyclerCharacterClickHandler
                        ) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    class ViewHolder(private val view: View,
                     private val listener: RecyclerCharacterClickHandler
                     ): RecyclerView.ViewHolder(view){
        private val imageType: ImageView = view.findViewById(R.id.image_itemCharacter)
        private val textName: TextView = view.findViewById(R.id.name_itemCharacter)
        private val textSpecies: TextView = view.findViewById(R.id.species_itemCharacter)
        private val textStatus: TextView = view.findViewById(R.id.status_itemCharacter)
        private val layoutCharacter: ConstraintLayout = view.findViewById(R.id.layout_itemCharacter)

        fun setData(character: Character){
            textName.text = character.name
            textSpecies.text = character.species
            textStatus.text = character.status
            imageType.load(character.image){
                transformations(CircleCropTransformation())
                error(R.drawable.ic_error)
                diskCachePolicy(CachePolicy.DISABLED)
                memoryCachePolicy(CachePolicy.DISABLED)
            }
            layoutCharacter.setOnClickListener{
                listener.onCharacterClicked(character)
            }
        }
    }

    interface RecyclerCharacterClickHandler{
        fun onCharacterClicked(character: Character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_character, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}

