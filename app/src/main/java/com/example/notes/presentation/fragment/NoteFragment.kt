package com.example.notes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.domain.model.NoteDomain
import com.example.notes.R
import com.example.notes.app.App
import com.example.notes.databinding.FragmentNoteBinding
import com.example.notes.di.ViewModelFactory
import com.example.notes.presentation.activity.MainActivity
import com.example.notes.presentation.viewmodel.NoteViewModel
import com.google.android.material.transition.MaterialContainerTransform
import javax.inject.Inject

class NoteFragment : Fragment(R.layout.fragment_note) {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: NoteViewModel
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MainActivity).applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
        arguments?.let { viewModel.setNote(it.getSerializable("note") as NoteDomain) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initButton()
        observers()
        sharedElementEnterTransition = MaterialContainerTransform()
        ViewCompat.setTransitionName(binding.root, viewModel.note.value?.id?.toString())
    }

    private fun observers() {
        viewModel.colorsLayoutVisibility.observe(viewLifecycleOwner) { visibility ->
            if (visibility && binding.includeColor.colorLayout.visibility != View.VISIBLE)
                binding.includeColor.colorLayout.visibleWithAnimation(R.anim.slide_bottom_up_anim)
            else if (!visibility && binding.includeColor.colorLayout.visibility != View.INVISIBLE)
                binding.includeColor.colorLayout.invisibleWithAnimation(R.anim.slide_bottom_down_anim)
        }
    }

    private fun initButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.deleteBtn.setOnClickListener {
            viewModel.clearNote()
            findNavController().navigateUp()
        }
    }
}