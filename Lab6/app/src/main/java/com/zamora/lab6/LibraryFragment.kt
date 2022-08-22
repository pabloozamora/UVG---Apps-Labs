package com.zamora.lab6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 * Use the [LibraryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LibraryFragment : Fragment(R.layout.fragment_library) {

    private lateinit var addSongButton: ImageView
    private lateinit var favSongsCounter: TextView
    private var count: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addSongButton = view.findViewById(R.id.add_song)
        favSongsCounter = view.findViewById(R.id.liked_songs)

        initListeners()
    }


    private fun initListeners(){
        addSongButton.setOnClickListener {
            count++
            favSongsCounter.text = count.toString()
        }
    }

}