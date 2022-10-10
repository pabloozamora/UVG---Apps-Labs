package com.zamora.lab12.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.zamora.lab12.R
import com.zamora.lab12.databinding.FragmentHomeBinding
import com.zamora.lab12.viewmodels.HomeViewModel
import com.zamora.lab12.viewmodels.SessionViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val sessionViewModel: SessionViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var listener: Job

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

        setObservables()
        setListeners()

    }

    private fun setListeners(){
        binding.buttonHomeFragmentLogOut.setOnClickListener{
            sessionViewModel.logout()
        }

        binding.buttonHomeFragmentKeepSessionActive.setOnClickListener {
            sessionViewModel.keepSessionActive()
        }
        binding.buttonHomeFragmentDefault.setOnClickListener {
            homeViewModel.setDefaultState()
            loadingScreen("default")

        }

        binding.buttonHomeFragmentEmpty.setOnClickListener {
            homeViewModel.setEmptyState()
            loadingScreen("empty")
        }

        binding.buttonHomeFragmentFailure.setOnClickListener {
            homeViewModel.setFailureState()
            loadingScreen("failure")
        }

        binding.buttonHomeFramentSuccess.setOnClickListener {
            homeViewModel.setSuccessState()
            loadingScreen("success")
        }

    }

    private fun setObservables() {
        lifecycleScope.launchWhenStarted {
            sessionViewModel.validAuthToken.collectLatest { authToken ->
                if (authToken.auth == false) {
                    val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                    requireView().findNavController().navigate(action)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.uiState.collectLatest {
                showState(it)
            }
        }
    }

    private fun showState(state: HomeViewModel.HomeFragmentState){
        when(state){
            is HomeViewModel.HomeFragmentState.Default -> {
                binding.progressBarHomeFragment.isVisible = false
                binding.imageHomeFragmentStateImage.isVisible = true
                binding.textHomeFragmentShowText.isVisible = true
                binding.imageHomeFragmentStateImage.setImageResource(R.drawable.ic_android_black)
                binding.textHomeFragmentShowText.text = state.defaultMessage
                doneLoading()
            }
            is HomeViewModel.HomeFragmentState.Empty -> {
                binding.progressBarHomeFragment.isVisible = false
                binding.imageHomeFragmentStateImage.isVisible = true
                binding.textHomeFragmentShowText.isVisible = true
                binding.imageHomeFragmentStateImage.setImageResource(R.drawable.ic_empty)
                binding.textHomeFragmentShowText.text = state.emptyMessage
                doneLoading()
            }
            is HomeViewModel.HomeFragmentState.Failure -> {
                binding.progressBarHomeFragment.isVisible = false
                binding.imageHomeFragmentStateImage.isVisible = true
                binding.textHomeFragmentShowText.isVisible = true
                binding.imageHomeFragmentStateImage.setImageResource(R.drawable.ic_failure)
                binding.textHomeFragmentShowText.text = state.failureMessage
                doneLoading()
            }
            is HomeViewModel.HomeFragmentState.Loading -> {
                binding.imageHomeFragmentStateImage.isVisible = false
                binding.textHomeFragmentShowText.isVisible = false
                binding.progressBarHomeFragment.isVisible = true

            }
            is HomeViewModel.HomeFragmentState.Success -> {
                binding.progressBarHomeFragment.isVisible = false
                binding.imageHomeFragmentStateImage.isVisible = true
                binding.textHomeFragmentShowText.isVisible = true
                binding.imageHomeFragmentStateImage.setImageResource(R.drawable.ic_success)
                binding.textHomeFragmentShowText.text = state.successMessage
                doneLoading()
            }
        }
    }

    private fun loadingScreen(state: String){
        binding.imageHomeFragmentStateImage.isVisible = false
        binding.textHomeFragmentShowText.isVisible = false
        binding.progressBarHomeFragment.isVisible = true
        when(state){
            "default" -> {
                binding.buttonHomeFragmentEmpty.isEnabled = false
                binding.buttonHomeFragmentFailure.isEnabled = false
                binding.buttonHomeFramentSuccess.isEnabled = false
            }
            "empty" -> {
                binding.buttonHomeFragmentFailure.isEnabled = false
                binding.buttonHomeFramentSuccess.isEnabled = false
                binding.buttonHomeFragmentDefault.isEnabled = false
            }
            "failure" -> {
                binding.buttonHomeFragmentEmpty.isEnabled = false
                binding.buttonHomeFramentSuccess.isEnabled = false
                binding.buttonHomeFragmentDefault.isEnabled = false
            }
            "success" -> {
                    binding.buttonHomeFragmentEmpty.isEnabled = false
                    binding.buttonHomeFragmentFailure.isEnabled = false
                    binding.buttonHomeFragmentDefault.isEnabled = false
                }
            }
        }

        private fun doneLoading(){
            binding.imageHomeFragmentStateImage.isVisible = true
            binding.buttonHomeFragmentEmpty.isEnabled = true
            binding.buttonHomeFragmentFailure.isEnabled = true
            binding.buttonHomeFramentSuccess.isEnabled = true
            binding.buttonHomeFragmentDefault.isEnabled = true
        }
    }


