package com.zamora.lab12.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zamora.lab12.R
import com.zamora.lab12.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

    }

    private fun setListeners(){
        binding.buttonHomeFragmentDefault.setOnClickListener {

        }

        binding.buttonHomeFragmentEmpty.setOnClickListener {

        }
    }

    fun showState(state: UiState){
        when(state){
            is UiState.Default -> TODO()
            is UiState.Empty -> TODO()
            is UiState.Failure -> TODO()
            UiState.Loading -> TODO()
            is UiState.Success -> TODO()
        }
    }
}