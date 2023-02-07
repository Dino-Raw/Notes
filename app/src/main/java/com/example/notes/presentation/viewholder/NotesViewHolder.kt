package com.example.notes.presentation.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.NoteDomain
import com.example.notes.R
import com.example.notes.databinding.RowNoteBinding

class NotesViewHolder(private val binding: RowNoteBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(note: NoteDomain) {
        binding.title = note.title
        binding.description = note.description
        binding.color = note.color
        binding.executePendingBindings()
    }

    fun setOnClick(note: NoteDomain) {
        ViewCompat.setTransitionName(binding.root, note.id?.toString())

        val bundle = bundleOf("note" to note)

        itemView.findNavController().navigate(
            R.id.action_homeFragment_to_noteFragment,
            bundle,
            null,
            FragmentNavigatorExtras(itemView to note.id?.toString()!!)
        )
    }
}