package com.example.notes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.app.App
import com.example.notes.databinding.FragmentHomeBinding
import com.example.notes.di.ViewModelFactory
import com.example.notes.presentation.activity.MainActivity
import com.example.notes.presentation.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment: Fragment(R.layout.fragment_home) {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ((activity as MainActivity).applicationContext as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        observers()
        initButton()
        return binding.root
    }

    private fun initButton() {
        binding.createNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_noteFragment)
        }
    }

    private fun observers() {
        viewModel.listNote.observe(viewLifecycleOwner) {notes ->
            if (notes.isNotEmpty())
                viewModel.setAdapter()
        }
    }
}