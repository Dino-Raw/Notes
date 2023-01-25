package com.example.notes.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.NoteDomain
import com.example.domain.usecase.DeleteNoteUseCase
import com.example.domain.usecase.InsertNoteUseCase
import com.example.domain.usecase.UpdateNoteUseCase
import com.example.notes.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel @Inject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
): ViewModel() {
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()
    val color: MutableLiveData<Int> = MutableLiveData()

    override fun onCleared() {
        if (title.value?.isNotEmpty() == true || description.value?.isNotEmpty() == true)
            insertNoteUseCase()
        super.onCleared()
    }

    fun insertNoteUseCase() {
        viewModelScope.launch(Dispatchers.IO) {
            insertNoteUseCase.execute(
                NoteDomain(
                    id = null,
                    title = title.value ?: "",
                    description = description.value ?: "",
                    color = color.value ?: R.color.white,
                )
            )
        }
    }
}