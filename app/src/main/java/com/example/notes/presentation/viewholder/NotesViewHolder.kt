package com.example.notes.presentation.viewholder

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.domain.model.NoteDomain
import com.example.notes.R
import com.example.notes.databinding.RowNoteBinding

class NotesViewHolder(private val binding: RowNoteBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(note: NoteDomain) {
        ViewCompat.setTransitionName(binding.root, note.id?.toString())

        binding.root.setOnClickListener {
            val bundle = bundleOf("note" to note)

            it.findNavController().navigate(
                R.id.action_homeFragment_to_noteFragment,
                bundle,
                null,
                FragmentNavigatorExtras(it to note.id?.toString()!!)
            )
        }

        binding.title = note.title
        binding.description = note.description
        binding.color = note.color
        binding.executePendingBindings()
    }
}