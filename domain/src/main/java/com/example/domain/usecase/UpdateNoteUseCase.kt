package com.example.domain.usecase

import com.example.domain.model.NoteDomain
import com.example.domain.repository.Repository

class UpdateNoteUseCase(
    private val repository: Repository
) {
    suspend fun execute(note: NoteDomain) {
        repository.updateNote(note = note)
    }
}