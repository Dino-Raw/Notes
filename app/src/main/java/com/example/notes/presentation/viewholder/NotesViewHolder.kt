package com.example.notes.presentation.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.NoteDomain
import com.example.notes.databinding.RowNoteBinding

class NotesViewHolder(private val binding: RowNoteBinding): RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(note: NoteDomain) {
        binding.title = note.title
        binding.description = note.description
        binding.noteCard.background = binding.root.context.resources.getDrawable(note.color)
        binding.executePendingBindings()
    }
}