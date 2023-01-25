package com.example.notes.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.NoteDomain
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.GetNotesUseCase
import com.example.notes.presentation.adapter.NotesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
): ViewModel() {
    private val _listNotes: MutableLiveData<MutableList<NoteDomain>> = MutableLiveData()
    val listNote: LiveData<MutableList<NoteDomain>> = _listNotes
    val notesAdapter = NotesAdapter()

    init {
        getNotes()
    }

    fun setAdapter() {
        notesAdapter.setData(_listNotes.value as ArrayList<NoteDomain>)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val notes = mutableListOf<NoteDomain>()
            notes.addAll(getNotesUseCase.execute())
            if (notes.isNotEmpty())
                _listNotes.postValue(notes)
        }
    }

    fun deleteNote() {

    }
}