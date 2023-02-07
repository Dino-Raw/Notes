package com.example.notes.presentation.viewmodel

import androidx.lifecycle.*
import com.example.domain.model.NoteDomain
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.GetNotesUseCase
import com.example.domain.usecase.UpdateNoteUseCase
import com.example.notes.presentation.adapter.NotesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    val notesAdapter: NotesAdapter,
    val notesPinnedAdapter: NotesAdapter,
) : ViewModel() {
    val notes = getNotesUseCase.execute().asLiveData(viewModelScope.coroutineContext)
    val notesPinned = getNotesUseCase.execute(pinned = true).asLiveData(viewModelScope.coroutineContext)

    private var _isToolbarVisibly: MutableLiveData<Boolean> = MutableLiveData(false)
    var isToolbarVisibly: LiveData<Boolean> = _isToolbarVisibly
    
    init {
        initMultiSelect()
        filterNotes()
    }

    private fun initMultiSelect() {
        val multiSelectSet = {
            val visibly =
                notesAdapter.selectListNotes.isNotEmpty() || notesPinnedAdapter.selectListNotes.isNotEmpty()
            if (_isToolbarVisibly.value != visibly)
                _isToolbarVisibly.value = visibly

        }
        val multiSelectGet = { _isToolbarVisibly.value!! }

        notesAdapter.multiSelectGet = multiSelectGet
        notesAdapter.multiSelectSet = multiSelectSet

        notesPinnedAdapter.multiSelectGet = multiSelectGet
        notesPinnedAdapter.multiSelectSet = multiSelectSet
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

    fun deleteSelectedNotes() {
        deleteNote(getSharedSelectedNotes())
        clearSelectedNotes()
    }

    fun invertPinSelectedNotes() {
        updateNote(getSharedSelectedNotes().map { it.apply { pinned = !pinned } })
        clearSelectedNotes()
    }

    fun clearSelectedNotes() {
        notesAdapter.clearSelectedItem()
        notesPinnedAdapter.clearSelectedItem()
        _isToolbarVisibly.value = false
    }

    private fun deleteNote(notes: List<NoteDomain>) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase.execute(*notes.toTypedArray())
        }
    }

    private fun updateNote(notes: List<NoteDomain>) {
        viewModelScope.launch(Dispatchers.IO) {
            updateNoteUseCase.execute(*notes.toTypedArray())
        }
    }

    private fun getSharedSelectedNotes(): List<NoteDomain> =
        notesAdapter.selectListNotes + notesPinnedAdapter.selectListNotes
}