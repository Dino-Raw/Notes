package com.example.notes.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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


class HomeFragment : Fragment(R.layout.fragment_home) {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var oldYDelta = 0

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
        initNestedScrollListener()
        observers()

        view.doOnLayout { startPostponedEnterTransition() }
    }

    private fun initNestedScrollListener() {
        binding.notesScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->

            val yDelta = scrollY - oldScrollY

            if (yDelta > 0 && oldYDelta <= 0) {
                binding.searchNote.invisibleWithAnimation(R.anim.slide_top_up_anim)
                binding.createNoteBtn.invisibleWithAnimation(R.anim.slide_bottom_down_anim)
            } else if (yDelta < 0 && oldYDelta >= 0) {
                binding.searchNote.visibleWithAnimation(R.anim.slide_top_down_anim)
                binding.createNoteBtn.visibleWithAnimation(R.anim.slide_bottom_up_anim)
            }

            oldYDelta = yDelta
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
        viewModel.notes.observe(viewLifecycleOwner) {
            viewModel.setNotesAdapter()
        }

        viewModel.notesPinned.observe(viewLifecycleOwner) {
            viewModel.setNotesPinnedAdapter()
        }

        viewModel.isToolbarVisibly.observe(viewLifecycleOwner) { visibility ->
            if (visibility && binding.toolbar.visibility != View.VISIBLE)
                binding.toolbar.visibleWithAnimation(R.anim.slide_top_down_anim)
            else if (!visibility && binding.toolbar.visibility != View.INVISIBLE)
                binding.toolbar.invisibleWithAnimation(R.anim.slide_top_up_anim)
        }
    }
}