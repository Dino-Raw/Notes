package com.example.notes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnScrollChangeListener
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.doOnLayout
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
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((activity as MainActivity).applicationContext as App).appComponent.inject(this)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(binding.root, savedInstanceState)
        postponeEnterTransition()

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        initButton()
        initSearchView()
        //initNestedScrollListener()
        observers()

        view.doOnLayout  { startPostponedEnterTransition() }
    }

    private fun initNestedScrollListener() {
        binding.notesScrollView.setOnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY + 12)
                binding.searchNote.visibility = View.GONE
                // viewModel.visibilityAfterScrolling.value = false
            if (scrollY < oldScrollY - 12)
                binding.searchNote.visibility = View.VISIBLE
                //viewModel.visibilityAfterScrolling.value = true

        }
    }

    private fun initButton() {
        binding.createNoteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_noteFragment)
        }
    }

    private fun initSearchView() {
        binding.searchNote.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterNotes(newText)
                return false
            }
        })
    }

    private fun observers() {
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
                viewModel.setNotesAdapter()
        }

        viewModel.notesPinned.observe(viewLifecycleOwner) { notes ->
                viewModel.setNotesPinnedAdapter()
        }
    }
}