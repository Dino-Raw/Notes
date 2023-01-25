package com.example.notes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.app.App
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.di.ViewModelFactory
import com.example.notes.presentation.activity.MainActivity
import com.example.notes.presentation.viewmodel.NoteViewModel
import javax.inject.Inject

class NoteFragment: Fragment(R.layout.fragment_note) {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: NoteViewModel
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ((activity as MainActivity).applicationContext as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
        _binding = FragmentNoteBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}