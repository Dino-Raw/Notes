package com.example.notes.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.NoteDomain
import com.example.notes.databinding.RowNoteBinding
import com.example.notes.presentation.viewholder.NotesViewHolder
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NotesAdapter @Inject constructor(): RecyclerView.Adapter<NotesViewHolder>(), Filterable {
    lateinit var multiSelectGet: () -> Boolean
    lateinit var multiSelectSet: () -> Unit
    private var listNotes = ArrayList<NoteDomain>()
    private var filterListNotes = ArrayList<NoteDomain>()
    var selectListNotes = ArrayList<NoteDomain>()

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

    override fun getItemCount() = filterListNotes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(filterListNotes[position])

        if (selectListNotes.contains(filterListNotes[position])) {
            holder.itemView.alpha = 0.3f
        } else {
            holder.itemView.alpha = 1.0f
        }

        holder.itemView.setOnClickListener {
            if (multiSelectGet.invoke()) {
                selectItem(holder, filterListNotes[position])
            } else {
                holder.setOnClick(filterListNotes[position])
            }
        }

        holder.itemView.setOnLongClickListener {
            if (!multiSelectGet.invoke()) {
                selectItem(holder, filterListNotes[position])
                true
            }
            else {
                false
            }
        }
    }

    private fun selectItem(holder: NotesViewHolder, note: NoteDomain) {
        if (selectListNotes.contains(note)) {
            selectListNotes.remove(note)
            holder.itemView.alpha = 1.0f
        } else {
            selectListNotes.add(note)
            holder.itemView.alpha = 0.3f
        }
        multiSelectSet.invoke()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelectedItem() {
        selectListNotes.clear()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch  = constraint.toString()
                filterListNotes = if (charSearch.isBlank()) {
                    listNotes
                } else {
                    val resultList = ArrayList<NoteDomain>()
                    for (note in listNotes) {
                        if (note.title.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))
                            || note.description.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(note)
                        }
                    }
                    resultList
                }

                return FilterResults().apply { values = filterListNotes }
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterListNotes = results?.values as ArrayList<NoteDomain>
                notifyDataSetChanged()
            }
        }
    }
}