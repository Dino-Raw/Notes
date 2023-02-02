package com.example.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.NoteDomain
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.InsertNoteUseCase
import com.example.domain.usecase.UpdateNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    noteDomain: NoteDomain,
) : ViewModel() {
    private val _note: MutableLiveData<NoteDomain> = MutableLiveData(noteDomain)
    val note: LiveData<NoteDomain> = _note

    private val _colorsLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    var colorsLayoutVisibility: LiveData<Boolean> = _colorsLayoutVisibility

    override fun onCleared() {
        closeNote()
        super.onCleared()
    }

    fun colorsLayoutVisibilityInverse() {
        _colorsLayoutVisibility.value = !_colorsLayoutVisibility.value!!
    }

    fun pinInverse() {
        _note.mutate { pinned = !pinned }
    }

    fun setColor(newColor: Int) {
        _note.mutate { color = newColor }
    }

    fun setNote(newNote: NoteDomain) {
        _note.value = newNote
    }

    fun clearNote() {
        _note.value?.apply {
            title = ""
            description = ""
        }
    }

    private fun deleteNote() {
        if (_note.value?.id != null)
            viewModelScope.launch(Dispatchers.IO + NonCancellable) {
                deleteNoteUseCase.execute(_note.value!!)
            }
    }

    private fun saveNote() {
        viewModelScope.launch(Dispatchers.IO + NonCancellable) {
            if (_note.value?.id == null) {
                insertNoteUseCase.execute(_note.value!!)
            } else {
                updateNoteUseCase.execute(_note.value!!)
            }
        }
    }

    private fun closeNote() {
        if (_note.value?.title?.isNotBlank() == true || _note.value?.description?.isNotBlank() == true)
            saveNote()
        else
            deleteNote()
    }
}