package com.example.notes.presentation.viewmodel

import androidx.lifecycle.*
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
    val notesAdapter: NotesAdapter,
    val notesPinnedAdapter: NotesAdapter,
): ViewModel() {
    val notes =
        getNotesUseCase.execute().asLiveData(viewModelScope.coroutineContext)
    val notesPinned =
        getNotesUseCase.execute(pinned = true).asLiveData(viewModelScope.coroutineContext)

    val visibilityAfterScrolling: MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        filterNotes()
    }

    fun filterNotes(text: String? = "") {
        notesAdapter.filter.filter(text)
        notesPinnedAdapter.filter.filter(text)
    }

    fun setNotesAdapter(note: List<NoteDomain>? = notes.value) {
        notesAdapter.setData(note as ArrayList<NoteDomain>)
    }

    fun setNotesPinnedAdapter() {
        notesPinnedAdapter.setData(notesPinned.value as ArrayList<NoteDomain>)
    }

    fun deleteNote(note: NoteDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase.execute(note)
        }
    }
}