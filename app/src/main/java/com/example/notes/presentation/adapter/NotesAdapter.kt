package com.example.notes.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.NoteDomain
import com.example.notes.databinding.RowNoteBinding
import com.example.notes.presentation.viewholder.NotesViewHolder
import javax.inject.Inject

class NotesAdapter @Inject constructor(): RecyclerView.Adapter<NotesViewHolder>() {
    private var listNotes = ArrayList<NoteDomain>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listNotes: ArrayList<NoteDomain>) {
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowNoteBinding.inflate(layoutInflater)
        return NotesViewHolder(binding)
    }

    override fun getItemCount() = listNotes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(listNotes[position])


    }
}