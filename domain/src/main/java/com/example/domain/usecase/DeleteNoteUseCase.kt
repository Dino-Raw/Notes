package com.example.domain.usecase

import com.example.domain.model.NoteDomain
import com.example.domain.repository.Repository

class DeleteNoteUseCase(
    private val repository: Repository
) {
    suspend fun execute(note: NoteDomain) {
        repository.deleteNote(note = note)
    }
}